package team.combinatorics.midtermproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import team.combinatorics.midtermproject.model.po.EmployPO;
import team.combinatorics.midtermproject.model.po.ManagePO;

@Repository
public interface EmployDao {

    int insertEmploy(@Param("employ") EmployPO record);

    int insertEmployByManager(@Param("manager") ManagePO managePO);

    int updateByEmployee(@Param("employ") EmployPO record);

    int updateByManager(@Param("manager") ManagePO managePO);

    int deleteByEmployee(Integer employeeWid);

    int deleteByEmployer(Integer employerWid);

    int deleteByDid(Integer did);

    EmployPO selectByPrimaryKey(Integer employeeWid);

    List<EmployPO> selectAll();

    List<EmployPO> selectByEmployer(Integer employerWid);

    int countByEmployer(Integer employerWid);

}