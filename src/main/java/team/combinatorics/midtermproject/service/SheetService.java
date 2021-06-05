package team.combinatorics.midtermproject.service;

import team.combinatorics.midtermproject.model.dto.AllServiceDTO;
import team.combinatorics.midtermproject.model.dto.ServiceDTO;
import team.combinatorics.midtermproject.model.po.IdGroup;

public interface SheetService {

    Integer addNewSheet(ServiceDTO serviceDTO);

    void updateSheet(ServiceDTO serviceDTO);

    void updateSheetWid(IdGroup idGroup);

    void deleteSheet(Integer sid);

    ServiceDTO getServiceBySid(Integer sid);

    AllServiceDTO getServiceByDid(Integer did);

    AllServiceDTO getServiceByWid(Integer wid);

    AllServiceDTO getServiceByUid(Integer uid);

    AllServiceDTO getAllService();

}
