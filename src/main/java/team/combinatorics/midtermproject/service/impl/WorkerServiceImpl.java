package team.combinatorics.midtermproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.combinatorics.midtermproject.dao.EmployDao;
import team.combinatorics.midtermproject.dao.ManageDao;
import team.combinatorics.midtermproject.dao.WorkerDao;
import team.combinatorics.midtermproject.exception.ErrorInfoEnum;
import team.combinatorics.midtermproject.exception.KnownException;
import team.combinatorics.midtermproject.model.dto.AllWorkerDTO;
import team.combinatorics.midtermproject.model.dto.WorkerDTO;
import team.combinatorics.midtermproject.model.po.EmployPO;
import team.combinatorics.midtermproject.model.po.ManagePO;
import team.combinatorics.midtermproject.model.po.WorkerPO;
import team.combinatorics.midtermproject.service.WorkerService;

import java.util.List;
import java.util.Vector;

@Service
@AllArgsConstructor
public class WorkerServiceImpl implements WorkerService {
    private final WorkerDao workerDao;
    private final ManageDao manageDao;
    private final EmployDao employDao;

    // 普通员工的添加
    // 同时也要修改employ表的信息。这个在sql内用触发器实现了
    // 返回wid给前端
    @Override
    public Integer addNewWorker(WorkerDTO workerDTO) {
        System.out.println("[添加员工]员工姓名："+workerDTO.getWorkerName()+"，员工部门id："+workerDTO.getDid());
        if(workerDTO.getDid()==null)
            throw new KnownException(ErrorInfoEnum.WORKER_INSERT_ERROR);
        WorkerPO workerPO = WorkerPO.builder().
                                workerName(workerDTO.getWorkerName()).
                                salary(workerDTO.getSalary()).
                                did(workerDTO.getDid()).
                                phoneNumber(workerDTO.getPhoneNumber()).
                                email(workerDTO.getEmail()).
                                build();
        int num = workerDao.insert(workerPO);
        if(num<1 || workerPO.getWid()==null)
            throw new KnownException(ErrorInfoEnum.WORKER_INSERT_ERROR);
        return workerPO.getWid();
    }

    // 普通员工的更新
    // 如果修改了did，也要同时更新employ信息，用事务实现，因为要判断manager的情况
    @Override
    @Transactional
    public void updateWorker(WorkerDTO workerDTO) {
        System.out.println("[修改员工信息]：员工id："+workerDTO.getWid());

        // 更新worker表
        if(workerDTO.getWid()==null)
            throw new KnownException(ErrorInfoEnum.WORKER_UPDATE_ERROR);
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
            throw new KnownException(ErrorInfoEnum.WORKER_UPDATE_ERROR);

        // 如果did修改了，同时更新employ表
        if(workerPO.getDid()!=null) {
            ManagePO managePO = manageDao.selectByDid(workerPO.getDid());
            // 如果新部门有经理，修改表项，没有经理则删除表项
            if(managePO!=null) {
                EmployPO employPO = EmployPO.builder().
                                        employeeWid(workerPO.getWid()).
                                        employerWid(managePO.getManagerWid()).
                                        build();
                // 如果原来存在，则更新，不存在则添加
                /*TODO: 考虑用on duplicate/replace into替换*/
                if(employDao.selectByPrimaryKey(workerPO.getWid())!=null)
                    employDao.updateByEmployee(employPO);
                else
                    employDao.insertEmploy(employPO);
            }
            else {
                // 原来没有，不起作用，原来有，则删除
                employDao.deleteByEmployee(workerPO.getWid());
            }
        }

    }

    // 普通员工的删除
    // 同时也要修改employ表的信息。这个在sql内用触发器实现了
    @Override
    public void deleteWorker(Integer wid) {
        System.out.println("[删除员工]待删除员工的id为："+wid);
        int num = workerDao.deleteByPrimaryKey(wid);
        if(num<1)
            throw new KnownException(ErrorInfoEnum.WORKER_DELETE_ERROR);
    }

    // 获取员工的基本信息
    @Override
    public WorkerDTO getWorkerInfoByWid(Integer wid) {
        System.out.println("[查询员工信息]员工id："+wid.toString());

        WorkerPO workerPO = workerDao.selectByPrimaryKey(wid);
        if(workerPO==null)
            throw new KnownException(ErrorInfoEnum.WORKER_SELECT_ERROR);

        return WorkerDTO.builder().
                wid(wid).
                workerName(workerPO.getWorkerName()).
                salary(workerPO.getSalary()).
                did(workerPO.getDid()).
                phoneNumber(workerPO.getPhoneNumber()).
                email(workerPO.getEmail()).
                build();
    }

    // 获取员工对应经理的信息
    @Override
    @Transactional
    public WorkerDTO getWorkerManagerByWid(Integer wid) {
        System.out.println("[查询员工经理]员工id："+wid.toString());

        // 查询员工信息
        WorkerPO workerPO = workerDao.selectByPrimaryKey(wid);
        if(workerPO==null)
            throw new KnownException(ErrorInfoEnum.WORKER_SELECT_ERROR);

        // 查询经理信息
        ManagePO managePO = manageDao.selectByDid(workerPO.getDid());
        if(managePO==null)
            return null;

        WorkerPO manager = workerDao.selectByPrimaryKey(managePO.getManagerWid());
        return WorkerDTO.builder().
                wid(manager.getWid()).
                workerName(manager.getWorkerName()).
                salary(manager.getSalary()).
                did(manager.getDid()).
                phoneNumber(manager.getPhoneNumber()).
                email(manager.getEmail()).
                build();
    }

    // 获取所有员工的信息，以及员工总人数，平均薪水
    @Override
    @Transactional
    public AllWorkerDTO getAllWorker() {
        System.out.println("[获取所有员工的信息]");
        List<WorkerPO> workerPOList = workerDao.selectAll();
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

        Integer allWorkerNum = workerDao.countAll();
        Double allAvgSalary = workerDao.calculateAllAvgSalary();
        if(allAvgSalary==null)
            allAvgSalary = 0.;

        return AllWorkerDTO.builder().
                allWorker(workerDTOList).
                allWorkerNum(allWorkerNum).
                allWorkerAvgSalary(allAvgSalary).
                build();
    }

}
