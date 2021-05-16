package team.combinatorics.midtermproject.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import team.combinatorics.midtermproject.model.po.DepartmentPO;

@Repository
public interface DepartmentDao {

    int deleteByPrimaryKey(Integer did);


    int insert(DepartmentPO record);


    DepartmentPO selectByPrimaryKey(Integer did);


    List<DepartmentPO> selectAll();


    int updateByPrimaryKey(DepartmentPO record);
}