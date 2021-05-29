package team.combinatorics.midtermproject.service;

import team.combinatorics.midtermproject.model.dto.AllWorkerDTO;
import team.combinatorics.midtermproject.model.dto.ManageDTO;
import team.combinatorics.midtermproject.model.dto.WorkerDTO;

public interface ManagerService {

    Integer addNewManager(WorkerDTO workerDTO);

    void changeWorkerToManager(ManageDTO manageDTO);

    void updateManager(WorkerDTO workerDTO);

    void deleteManager(Integer wid);

    WorkerDTO getManagerByDid(Integer did);

    AllWorkerDTO getAllManager();

    AllWorkerDTO getWorkerByManager(Integer wid);



}
