package team.combinatorics.midtermproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import team.combinatorics.midtermproject.dao.ManageDao;
import team.combinatorics.midtermproject.dao.WorkerDao;
import team.combinatorics.midtermproject.exception.ErrorInfoEnum;
import team.combinatorics.midtermproject.exception.KnownException;
import team.combinatorics.midtermproject.model.dto.AllWorkerDTO;
import team.combinatorics.midtermproject.model.dto.ManageDTO;
import team.combinatorics.midtermproject.model.dto.WorkerDTO;
import team.combinatorics.midtermproject.model.po.ManagePO;
import team.combinatorics.midtermproject.model.po.WorkerPO;
import team.combinatorics.midtermproject.service.ManagerService;

@Service
@AllArgsConstructor
public class ManagerServiceImpl implements ManagerService {
    private final WorkerDao workerDao;
    private final ManageDao manageDao;

    // 直接加入一个员工信息+manager
    @Override
    public Integer addNewManager(WorkerDTO workerDTO) {
        System.out.println("[添加新经理]经理姓名："+workerDTO.getWorkerName()+"，经理部门id："+workerDTO.getDid());

        // 先在worker表内加入一个新worker
        if(workerDTO.getDid()==null)
            throw new KnownException(ErrorInfoEnum.MANAGER_INSERT_ERROR);
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
        return workerPO.getWid();
    }

    // 把worker变为manager
    @Override
    public void changeWorkerToManager(ManageDTO manageDTO) {
        System.out.println("[提拔员工为经理]经理的员工id："+manageDTO.getManagerWid()+"，经理部门id："+manageDTO.getDid());
        ManagePO managePO = ManagePO.builder().
                                did(manageDTO.getDid()).
                                managerWid(manageDTO.getManagerWid()).
                                build();
        int num = manageDao.insertManager(managePO);
        if(num<1)
            throw new KnownException(ErrorInfoEnum.MANAGER_INSERT_ERROR);
    }

    // 更新manage信息，要检查wid是否为一个manager
    // 当更新did时，同时修改employ表
    @Override
    public void updateManager(Integer wid) {

    }

    @Override
    public void deleteManager(Integer wid) {

    }

    @Override
    public WorkerDTO getManagerByDid(Integer did) {
        return null;
    }

    @Override
    public AllWorkerDTO getAllManager() {
        return null;
    }

    @Override
    public AllWorkerDTO getWorkerByManager(Integer wid) {
        return null;
    }

}
