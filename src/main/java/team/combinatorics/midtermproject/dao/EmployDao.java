package team.combinatorics.midtermproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import team.combinatorics.midtermproject.model.po.EmployPO;

@Repository
public interface EmployDao {

    int insertEmploy(@Param("employ") EmployPO record);

    int updateByEmployee(@Param("employ") EmployPO record);

    int deleteByEmployee(Integer employeeWid);

    int deleteByEmployer(Integer employerWid);

    int deleteByDid(Integer did);

    EmployPO selectByPrimaryKey(Integer employeeWid);

    List<EmployPO> selectAll();

}