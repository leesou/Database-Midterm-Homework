package team.combinatorics.midtermproject.service;

import team.combinatorics.midtermproject.model.dto.AllWorkerDTO;
import team.combinatorics.midtermproject.model.dto.WorkerDTO;

public interface WorkerService {

    Integer addNewWorker(WorkerDTO workerDTO);

    void updateWorker(WorkerDTO workerDTO);

    void deleteWorker(Integer wid);

    WorkerDTO getWorkerInfoByWid(Integer wid);

    WorkerDTO getWorkerManagerByWid(Integer wid);

    AllWorkerDTO getAllWorker();

}
