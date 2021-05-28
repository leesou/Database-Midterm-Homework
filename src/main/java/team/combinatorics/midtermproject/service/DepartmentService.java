package team.combinatorics.midtermproject.service;

import team.combinatorics.midtermproject.model.dto.AllDepartmentDTO;
import team.combinatorics.midtermproject.model.dto.AllWorkerDTO;
import team.combinatorics.midtermproject.model.dto.DepartmentDTO;
import team.combinatorics.midtermproject.model.dto.WorkerDTO;

import java.util.List;

public interface DepartmentService {

    void addNewDepartment(DepartmentDTO departmentDTO);

    void updateDepartment(DepartmentDTO departmentDTO);

    void deleteDepartment(Integer did);

    AllDepartmentDTO getAllDepartment();

    DepartmentDTO getDepartmentInfoByDid(Integer did);

    AllWorkerDTO getDepartmentWorkerByDid(Integer did);

}
