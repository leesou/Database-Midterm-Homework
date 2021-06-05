package team.combinatorics.midtermproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.combinatorics.midtermproject.dao.ServiceDao;
import team.combinatorics.midtermproject.dao.WorkerDao;
import team.combinatorics.midtermproject.exception.ErrorInfoEnum;
import team.combinatorics.midtermproject.exception.KnownException;
import team.combinatorics.midtermproject.model.dto.AllServiceDTO;
import team.combinatorics.midtermproject.model.dto.ServiceDTO;
import team.combinatorics.midtermproject.model.po.IdGroup;
import team.combinatorics.midtermproject.model.po.ServicePO;
import team.combinatorics.midtermproject.service.SheetService;

import java.util.List;
import java.util.Vector;

@Service
@AllArgsConstructor
public class SheetServiceImpl implements SheetService {
    private final ServiceDao serviceDao;
    private final WorkerDao workerDao;

    // 添加新保修单
    @Override
    public Integer addNewSheet(ServiceDTO serviceDTO) {
        System.out.println("[添加保修单]商品id："+serviceDTO.getPid().toString()+"，员工id："+serviceDTO.getWid().toString());
        if(serviceDTO.getPid()==null || serviceDTO.getWid()==null)
            throw new KnownException(ErrorInfoEnum.SHEET_INSERT_ERROR);

        ServicePO servicePO = ServicePO.builder().
                serviceType(serviceDTO.getServiceType()).
                description(serviceDTO.getDescription()).
                state(serviceDTO.getState()).
                wid(serviceDTO.getWid()).
                pid(serviceDTO.getPid()).
                build();
        int num = serviceDao.insert(servicePO);
        if(num<1 || servicePO.getSid()==null)
            throw new KnownException(ErrorInfoEnum.SHEET_INSERT_ERROR);

        return servicePO.getSid();
    }

    // 更新保修单信息
    @Override
    public void updateSheet(ServiceDTO serviceDTO) {
        System.out.println("[添加保修单]保修单id："+serviceDTO.getSid().toString());
        if(serviceDTO.getSid()==null)
            throw new KnownException(ErrorInfoEnum.SHEET_UPDATE_ERROR);

        ServicePO servicePO = ServicePO.builder().
                serviceType(serviceDTO.getServiceType()).
                description(serviceDTO.getDescription()).
                state(serviceDTO.getState()).
                wid(serviceDTO.getWid()).
                pid(serviceDTO.getPid()).
                build();
        int num = serviceDao.update(servicePO);
        if(num<1)
            throw new KnownException(ErrorInfoEnum.SHEET_UPDATE_ERROR);
    }

    @Override
    @Transactional
    public void updateSheetWid(IdGroup idGroup) {
        System.out.println("[维修单交接]旧的员工id："+idGroup.getOldId()+"，新的员工id："+idGroup.getNewId());

        // 检查worker是否存在
        if(workerDao.selectByPrimaryKey(idGroup.getOldId())==null || workerDao.selectByPrimaryKey(idGroup.getNewId())==null)
            throw new KnownException(ErrorInfoEnum.SHEET_WID_ERROR);

        int num = serviceDao.updateWid(idGroup);
        System.out.println("[维修单交接]交接数量："+num);
    }

    // 删除保修单
    @Override
    public void deleteSheet(Integer sid) {
        System.out.println("[删除保修单]保修单id："+sid.toString());
        int num = serviceDao.deleteByPrimaryKey(sid);
        if(num<1)
            throw new KnownException(ErrorInfoEnum.SHEET_DELETE_ERROR);
    }

    // 获取单个保修单信息
    @Override
    public ServiceDTO getServiceBySid(Integer sid) {
        System.out.println("[获取单个保修单信息]保修单id："+sid.toString());
        ServicePO servicePO = serviceDao.selectByPrimaryKey(sid);
        if(servicePO==null)
            throw new KnownException(ErrorInfoEnum.SHEET_SELECT_ERROR);

        return ServiceDTO.builder().
                sid(servicePO.getSid()).
                serviceType(servicePO.getServiceType()).
                description(servicePO.getDescription()).
                state(servicePO.getState()).
                wid(servicePO.getWid()).
                pid(servicePO.getPid()).
                build();
    }

    // 获取单个部门的保修单信息
    @Override
    public AllServiceDTO getServiceByDid(Integer did) {
        System.out.println("[获取单个部门的保修单信息]部门id"+did.toString());
        List<ServicePO> servicePOList = serviceDao.selectByDid(did);
        List<ServiceDTO> serviceDTOList = new Vector<>();
        for(ServicePO servicePO:servicePOList) {
            ServiceDTO serviceDTO = ServiceDTO.builder().
                    sid(servicePO.getSid()).
                    serviceType(servicePO.getServiceType()).
                    description(servicePO.getDescription()).
                    state(servicePO.getState()).
                    wid(servicePO.getWid()).
                    pid(servicePO.getPid()).
                    build();
            serviceDTOList.add(serviceDTO);
        }

        Integer allServiceNum = serviceDao.countByDid(did);

        return AllServiceDTO.builder().
                allService(serviceDTOList).
                allServiceNum(allServiceNum).
                build();
    }

    // 获取单个员工的保修单信息
    @Override
    @Transactional
    public AllServiceDTO getServiceByWid(Integer wid) {
        System.out.println("[获取单个员工的保修单信息]部门id"+wid.toString());
        List<ServicePO> servicePOList = serviceDao.selectByWid(wid);
        List<ServiceDTO> serviceDTOList = new Vector<>();
        for(ServicePO servicePO:servicePOList) {
            ServiceDTO serviceDTO = ServiceDTO.builder().
                    sid(servicePO.getSid()).
                    serviceType(servicePO.getServiceType()).
                    description(servicePO.getDescription()).
                    state(servicePO.getState()).
                    wid(servicePO.getWid()).
                    pid(servicePO.getPid()).
                    build();
            serviceDTOList.add(serviceDTO);
        }

        Integer allServiceNum = serviceDao.countByWid(wid);

        return AllServiceDTO.builder().
                allService(serviceDTOList).
                allServiceNum(allServiceNum).
                build();
    }

    // 获取单个用户的保修单信息
    @Override
    @Transactional
    public AllServiceDTO getServiceByUid(Integer uid) {
        System.out.println("[获取单个用户的保修单信息]部门id"+uid.toString());
        List<ServicePO> servicePOList = serviceDao.selectByUid(uid);
        List<ServiceDTO> serviceDTOList = new Vector<>();
        for(ServicePO servicePO:servicePOList) {
            ServiceDTO serviceDTO = ServiceDTO.builder().
                    sid(servicePO.getSid()).
                    serviceType(servicePO.getServiceType()).
                    description(servicePO.getDescription()).
                    state(servicePO.getState()).
                    wid(servicePO.getWid()).
                    pid(servicePO.getPid()).
                    build();
            serviceDTOList.add(serviceDTO);
        }

        Integer allServiceNum = serviceDao.countByUid(uid);

        return AllServiceDTO.builder().
                allService(serviceDTOList).
                allServiceNum(allServiceNum).
                build();
    }

    // 获取全部保修单信息
    @Override
    @Transactional
    public AllServiceDTO getAllService() {
        System.out.println("[获取全部保修单信息]");
        List<ServicePO> servicePOList = serviceDao.selectAll();
        List<ServiceDTO> serviceDTOList = new Vector<>();
        for(ServicePO servicePO:servicePOList) {
            ServiceDTO serviceDTO = ServiceDTO.builder().
                    sid(servicePO.getSid()).
                    serviceType(servicePO.getServiceType()).
                    description(servicePO.getDescription()).
                    state(servicePO.getState()).
                    wid(servicePO.getWid()).
                    pid(servicePO.getPid()).
                    build();
            serviceDTOList.add(serviceDTO);
        }

        Integer allServiceNum = serviceDao.countAll();

        return AllServiceDTO.builder().
                allService(serviceDTOList).
                allServiceNum(allServiceNum).
                build();
    }



}
