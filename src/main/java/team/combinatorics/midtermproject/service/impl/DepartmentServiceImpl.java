package team.combinatorics.midtermproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.combinatorics.midtermproject.dao.DepartmentDao;
import team.combinatorics.midtermproject.dao.EmployDao;
import team.combinatorics.midtermproject.dao.ManageDao;
import team.combinatorics.midtermproject.dao.WorkerDao;
import team.combinatorics.midtermproject.exception.ErrorInfoEnum;
import team.combinatorics.midtermproject.exception.KnownException;
import team.combinatorics.midtermproject.model.dto.AllDepartmentDTO;
import team.combinatorics.midtermproject.model.dto.AllWorkerDTO;
import team.combinatorics.midtermproject.model.dto.DepartmentDTO;
import team.combinatorics.midtermproject.model.dto.WorkerDTO;
import team.combinatorics.midtermproject.model.po.DepartmentPO;
import team.combinatorics.midtermproject.model.po.ManagePO;
import team.combinatorics.midtermproject.model.po.WorkerPO;
import team.combinatorics.midtermproject.service.DepartmentService;

import java.util.List;
import java.util.Vector;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentDao departmentDao;
    private final EmployDao employDao;
    private final ManageDao manageDao;
    private final WorkerDao workerDao;

    @Override
    public void addNewDepartment(DepartmentDTO departmentDTO) {
        System.out.println("[添加部门]部门名称："+departmentDTO.getDepartmentName());
        DepartmentPO departmentPO = DepartmentPO.builder().
                                        departmentName(departmentDTO.getDepartmentName()).
                                        build();
        int num = departmentDao.insert(departmentPO);
        if(num<1 || departmentPO.getDid()==null)
            throw new KnownException(ErrorInfoEnum.DEPARTMENT_INSERT_ERR);
        System.out.println("[添加部门]部门id："+departmentDTO.getDid());
    }

    @Override
    public void updateDepartment(DepartmentDTO departmentDTO) {
        System.out.println("[更新部门]部门id："+departmentDTO.getDid().toString()+"，新的部门名称："+departmentDTO.getDepartmentName());
        DepartmentPO departmentPO = DepartmentPO.builder().
                                        did(departmentDTO.getDid()).
                                        departmentName(departmentDTO.getDepartmentName()).
                                        build();
        int num = departmentDao.update(departmentPO);
        if(num<1)
            throw new KnownException(ErrorInfoEnum.DEPARTMENT_UPDATE_ERR);
    }

    // 删除部门时，要先删除其下的员工和经理，以及对应的雇佣关系
    // 使用事务保证一致性
    @Override
    @Transactional
    public void deleteDepartment(Integer did) {
        System.out.println("[删除部门]部门id："+did.toString());

        int numEmploy = employDao.deleteByDid(did);
        System.out.println("[删除部门]删除雇佣记录条数："+numEmploy);

        int numManager = manageDao.deleteManager(did);
        System.out.println("[删除部门]删除经理个数："+numManager);

        int numWorker = workerDao.deleteByDid(did);
        System.out.println("[删除部门]删除员工（含经理）人数："+numWorker);

        int num = departmentDao.deleteByPrimaryKey(did);
        if(num<1)
            throw new KnownException(ErrorInfoEnum.DEPARTMENT_DELETE_ERROR);
    }

    // 列出所有部门的did-部门名称-经理信息，以及部门个数
    @Override
    @Transactional
    public AllDepartmentDTO getAllDepartment() {
        System.out.println("[查询所有部门的信息]");

        // 获取所有部门的did-部门名称-经理信息
        List<DepartmentPO> allDepartmentP = departmentDao.selectAll();
        List<DepartmentDTO> allDepartmentD = new Vector<>();
        for(DepartmentPO departmentPO:allDepartmentP) {
            ManagePO managePO = manageDao.selectByDid(departmentPO.getDid());
            WorkerPO workerPO = workerDao.selectByPrimaryKey(managePO.getManagerWid());
            WorkerDTO workerDTO = WorkerDTO.builder().
                    wid(workerPO.getWid()).
                    workerName(workerPO.getWorkerName()).
                    salary(workerPO.getSalary()).
                    did(workerPO.getDid()).
                    phoneNumber(workerPO.getPhoneNumber()).
                    email(workerPO.getEmail()).
                    build();

            DepartmentDTO departmentDTO = DepartmentDTO.builder().
                                                did(departmentPO.getDid()).
                                                departmentName(departmentPO.getDepartmentName()).
                                                manager(workerDTO).
                                                build();
            allDepartmentD.add(departmentDTO);
        }

        // 获取部门个数
        Integer allDepartmentNum = departmentDao.countAll();

        return AllDepartmentDTO.builder().
                allDepartment(allDepartmentD).
                allDepartmentNum(allDepartmentNum).
                build();
    }

    // 根据did，获取did-部门名称-经理信息
    @Override
    @Transactional
    public DepartmentDTO getDepartmentInfoByDid(Integer did) {
        System.out.println("[获取部门信息]部门id："+did.toString());
        DepartmentPO departmentPO = departmentDao.selectByPrimaryKey(did);
        if(departmentPO==null)
            throw new KnownException(ErrorInfoEnum.DEPARTMENT_SELECT_ERROR);

        ManagePO managePO = manageDao.selectByDid(departmentPO.getDid());
        WorkerPO workerPO = workerDao.selectByPrimaryKey(managePO.getManagerWid());
        WorkerDTO workerDTO = WorkerDTO.builder().
                wid(workerPO.getWid()).
                workerName(workerPO.getWorkerName()).
                salary(workerPO.getSalary()).
                did(workerPO.getDid()).
                phoneNumber(workerPO.getPhoneNumber()).
                email(workerPO.getEmail()).
                build();
        return DepartmentDTO.builder().
                did(departmentPO.getDid()).
                departmentName(departmentPO.getDepartmentName()).
                manager(workerDTO).
                build();
    }

    // 根据did，获取部门内所有的员工信息（含经理），员工个数，员工平均薪水
    @Override
    @Transactional
    public AllWorkerDTO getDepartmentWorkerByDid(Integer did) {
        System.out.println("[查询部门员工]部门id："+did.toString());
        DepartmentPO departmentPO = departmentDao.selectByPrimaryKey(did);
        if(departmentPO==null)
            throw new KnownException(ErrorInfoEnum.DEPARTMENT_SELECT_ERROR);

        List<WorkerPO> workerPOs = workerDao.selectByDid(did);
        List<WorkerDTO> workerDTOs = new Vector<>();
        for(WorkerPO workerPO:workerPOs) {
            WorkerDTO workerDTO = WorkerDTO.builder().
                    wid(workerPO.getWid()).
                    workerName(workerPO.getWorkerName()).
                    salary(workerPO.getSalary()).
                    did(workerPO.getDid()).
                    phoneNumber(workerPO.getPhoneNumber()).
                    email(workerPO.getEmail()).
                    build();
            workerDTOs.add(workerDTO);
        }

        Integer workerNum = workerDao.countByDid(did);
        Float workerAvgSalary = workerDao.calculateAvgSalaryByDid(did);
        if(workerAvgSalary==null)
            workerAvgSalary = 0f;

        return AllWorkerDTO.builder().
                allWorker(workerDTOs).
                allWorkerNum(workerNum).
                allWorkerAvgSalary(workerAvgSalary).
                build();
    }

}
