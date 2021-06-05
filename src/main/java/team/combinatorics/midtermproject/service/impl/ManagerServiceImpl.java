package team.combinatorics.midtermproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.combinatorics.midtermproject.dao.EmployDao;
import team.combinatorics.midtermproject.dao.ManageDao;
import team.combinatorics.midtermproject.dao.ServiceDao;
import team.combinatorics.midtermproject.dao.WorkerDao;
import team.combinatorics.midtermproject.exception.ErrorInfoEnum;
import team.combinatorics.midtermproject.exception.KnownException;
import team.combinatorics.midtermproject.model.dto.AllWorkerDTO;
import team.combinatorics.midtermproject.model.dto.ManageDTO;
import team.combinatorics.midtermproject.model.dto.WorkerDTO;
import team.combinatorics.midtermproject.model.po.ManagePO;
import team.combinatorics.midtermproject.model.po.WorkerPO;
import team.combinatorics.midtermproject.service.ManagerService;

import java.util.List;
import java.util.Vector;

@Service
@AllArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final WorkerDao workerDao;
    private final ManageDao manageDao;
    private final EmployDao employDao;
    private final ServiceDao serviceDao;

    // 直接加入一个员工信息+manager
    // 同时修改employ
    @Override
    @Transactional
    public Integer addNewManager(WorkerDTO workerDTO) {
        System.out.println("[添加新经理]经理姓名："+workerDTO.getWorkerName()+"，经理部门id："+workerDTO.getDid());
        // 检查部门是否已有经理了
        if(manageDao.selectByDid(workerDTO.getDid())!=null)
            throw new KnownException(ErrorInfoEnum.MANAGER_INSERT_ERROR);

        // 先在worker表内加入一个新worker
        if(workerDTO.getDid()==null)
            throw new KnownException(ErrorInfoEnum.MANAGER_INSERT_ERROR);
        if(workerDTO.getDid()==1)
            throw new KnownException(ErrorInfoEnum.CHANGE_SHADE_ERROR);
        WorkerPO workerPO = WorkerPO.builder().
                workerName(workerDTO.getWorkerName()).
                salary(workerDTO.getSalary()).
                did(workerDTO.getDid()).
                phoneNumber(workerDTO.getPhoneNumber()).
                email(workerDTO.getEmail()).
                build();
        int num = workerDao.insert(workerPO);
        if(num<1 || workerPO.getWid()==null)
            throw new KnownException(ErrorInfoEnum.MANAGER_INSERT_ERROR);

        // 然后添加manage表项
        ManagePO managePO = ManagePO.builder().
                                did(workerPO.getDid()).
                                managerWid(workerPO.getWid()).
                                build();
        int num1 = manageDao.insertManager(managePO);
        if(num1<1)
            throw new KnownException(ErrorInfoEnum.MANAGER_INSERT_ERROR);

        // 最后修改employ
        int numEmploy = employDao.insertEmployByManager(managePO);
        System.out.println("[添加新经理]添加雇佣关系数量："+numEmploy);

        return workerPO.getWid();
    }

    // 把worker变为manager
    // 同时修改employ
    @Override
    @Transactional
    public void changeWorkerToManager(ManageDTO manageDTO) {
        System.out.println("[提拔员工为经理]经理的员工id："+manageDTO.getManagerWid()+"，经理部门id："+manageDTO.getDid());

        if(manageDTO.getDid()==1 || manageDTO.getManagerWid()==1)
            throw new KnownException(ErrorInfoEnum.CHANGE_SHADE_ERROR);

        // 检查是不是已经为经理了，或者部门已有经理了
        if(manageDao.selectByWid(manageDTO.getManagerWid())!=null || manageDao.selectByDid(manageDTO.getDid())==null)
            throw new KnownException(ErrorInfoEnum.MANAGER_INSERT_ERROR);


        ManagePO managePO = ManagePO.builder().
                                did(manageDTO.getDid()).
                                managerWid(manageDTO.getManagerWid()).
                                build();
        int num = manageDao.insertManager(managePO);
        if(num<1)
            throw new KnownException(ErrorInfoEnum.MANAGER_INSERT_ERROR);

        // 最后修改employ
        // 先删除作为employee的记录
        employDao.deleteByEmployee(managePO.getManagerWid());
        // 再添加employ
        int numEmploy = employDao.insertEmployByManager(managePO);
        System.out.println("[提拔员工为经理]添加雇佣关系数量："+numEmploy);

    }

    // 更新manage信息，要检查wid是否为一个manager
    // 当更新did时，同时修改employ表
    @Override
    @Transactional
    public void updateManager(WorkerDTO workerDTO) {
        System.out.println("[更新经理信息]经理的员工id："+workerDTO.getWid());

        if(workerDTO.getDid()==1 || workerDTO.getWid()==1)
            throw new KnownException(ErrorInfoEnum.CHANGE_SHADE_ERROR);

        // 先确定这是一位经理
        ManagePO managePO = manageDao.selectByWid(workerDTO.getWid());
        if(managePO==null || !managePO.getManagerWid().equals(workerDTO.getWid()))
            throw new KnownException(ErrorInfoEnum.MANAGER_UPDATE_ERROR);

        // 更新worker表
        if(workerDTO.getWid()==null)
            throw new KnownException(ErrorInfoEnum.MANAGER_UPDATE_ERROR);
        WorkerPO workerPO = WorkerPO.builder().
                wid(workerDTO.getWid()).
                workerName(workerDTO.getWorkerName()).
                salary(workerDTO.getSalary()).
                did(workerDTO.getDid()).
                phoneNumber(workerDTO.getPhoneNumber()).
                email(workerDTO.getEmail()).
                build();
        int num = workerDao.update(workerPO);
        if(num<1)
            throw new KnownException(ErrorInfoEnum.MANAGER_UPDATE_ERROR);

        // 如果更新了did
        if(workerPO.getDid()!=null) {
            // 先删除原来部门的employ和manage信息
            int numPrevEmploy = employDao.deleteByEmployer(workerPO.getWid());
            int numPrevDep = manageDao.deleteManagerByWid(workerPO.getWid());
            System.out.println("[更新经理信息]原来部门的雇佣数："+numPrevEmploy+"，删除manage表项数："+numPrevDep);
            // 检查新部门是否已有经理
            ManagePO newManagePO = manageDao.selectByDid(workerPO.getDid());
            if(newManagePO!=null) {
                // 如果有，使用update
                newManagePO.setManagerWid(workerPO.getWid());
                int numManage = manageDao.updateManager(newManagePO);
                int numEmploy = employDao.updateByManager(newManagePO);
                System.out.println("[更新经理信息]新部门更新的雇佣数："+numEmploy+"，更新manage表项数"+numManage);
            }
            else {
                // 没有，使用insert
                newManagePO = ManagePO.builder().
                        did(workerPO.getDid()).
                        managerWid(workerPO.getWid()).
                        build();
                int numManage = manageDao.insertManager(newManagePO);
                int numEmploy = employDao.insertEmployByManager(newManagePO);
                System.out.println("[更新经理信息]新部门增加的雇佣数："+numEmploy+"，添加manage表项数"+numManage);
            }
        }

    }

    // 删除manage
    // 同时删除worker和employ信息
    @Override
    @Transactional
    public void deleteManager(Integer wid) {
        System.out.println("[删除经理]经理的员工id："+wid);

        if(wid==1)
            throw new KnownException(ErrorInfoEnum.CHANGE_SHADE_ERROR);

        // 检查是否有未交接的维修单
        if(serviceDao.countByWid(wid)>0)
            throw new KnownException(ErrorInfoEnum.WORKER_SHEET_ERROR);

        int numManage = manageDao.deleteManagerByWid(wid);
        if(numManage<1)
            throw new KnownException(ErrorInfoEnum.MANAGER_DELETE_ERROR);

        int numEmploy = employDao.deleteByEmployer(wid);

        int numWorker = workerDao.deleteByPrimaryKey(wid);
        if(numWorker<1)
            throw new KnownException(ErrorInfoEnum.MANAGER_DELETE_ERROR);

        System.out.println("[删除经理]删除manage表项数："+numManage+"，删除employ表项数："+numEmploy+"，删除worker表项数："+numWorker);
    }

    // 查询某个部门的经理信息
    @Override
    @Transactional
    public WorkerDTO getManagerByDid(Integer did) {
        System.out.println("[查询单个部门的经理信息]部门id："+did);

        if(did==1)
            throw new KnownException(ErrorInfoEnum.CHANGE_SHADE_ERROR);

        // 检查是否存在经理
        ManagePO managePO = manageDao.selectByDid(did);
        if(managePO==null)
            throw new KnownException(ErrorInfoEnum.MANAGER_SELECT_ERROR);

        WorkerPO workerPO = workerDao.selectByPrimaryKey(managePO.getManagerWid());
        return WorkerDTO.builder().
                wid(workerPO.getWid()).
                workerName(workerPO.getWorkerName()).
                salary(workerPO.getSalary()).
                did(workerPO.getDid()).
                phoneNumber(workerPO.getPhoneNumber()).
                email(workerPO.getEmail()).
                build();
    }

    // 获取所有经理信息
    @Override
    @Transactional
    public AllWorkerDTO getAllManager() {
        System.out.println("[查询所有部门的经理信息]");

        List<ManagePO> managePOList = manageDao.selectAll();
        List<WorkerDTO> workerDTOList = new Vector<>();
        for(ManagePO managePO:managePOList) {
            WorkerPO workerPO = workerDao.selectByPrimaryKey(managePO.getManagerWid());
            WorkerDTO workerDTO = WorkerDTO.builder().
                    wid(workerPO.getWid()).
                    workerName(workerPO.getWorkerName()).
                    salary(workerPO.getSalary()).
                    did(workerPO.getDid()).
                    phoneNumber(workerPO.getPhoneNumber()).
                    email(workerPO.getEmail()).
                    build();
            workerDTOList.add(workerDTO);
        }

        Integer allManagerNum = manageDao.countAll();
        Double allManagerAvgSalary = workerDao.calculateAvgManagerSalary();
        if(allManagerAvgSalary==null)
            allManagerAvgSalary = 0.;

        return AllWorkerDTO.builder().
                allWorker(workerDTOList).
                allWorkerNum(allManagerNum).
                allWorkerAvgSalary(allManagerAvgSalary).
                build();
    }

    // 获取经理管理的员工信息
    @Override
    @Transactional
    public AllWorkerDTO getWorkerByManager(Integer wid) {
        System.out.println("[查询单个经理管理的员工信息]经理的员工id："+wid);

        if(wid==1)
            throw new KnownException(ErrorInfoEnum.CHANGE_SHADE_ERROR);

        if(manageDao.selectByWid(wid)==null)
            throw new KnownException(ErrorInfoEnum.WORKER_SELECT_ERROR);

        List<WorkerPO> workerPOList = workerDao.selectByManager(wid);
        List<WorkerDTO> workerDTOList = new Vector<>();
        for(WorkerPO workerPO:workerPOList) {
            WorkerDTO workerDTO = WorkerDTO.builder().
                    wid(workerPO.getWid()).
                    workerName(workerPO.getWorkerName()).
                    salary(workerPO.getSalary()).
                    did(workerPO.getDid()).
                    phoneNumber(workerPO.getPhoneNumber()).
                    email(workerPO.getEmail()).
                    build();
            workerDTOList.add(workerDTO);
        }

        Integer manageWorkerNum = employDao.countByEmployer(wid);
        Double manageWorkerAvgSalary = workerDao.calculateAvgSalaryByManager(wid);
        if(manageWorkerAvgSalary==null)
            manageWorkerAvgSalary = 0.;

        return AllWorkerDTO.builder().
                allWorker(workerDTOList).
                allWorkerNum(manageWorkerNum).
                allWorkerAvgSalary(manageWorkerAvgSalary).
                build();
    }

}
