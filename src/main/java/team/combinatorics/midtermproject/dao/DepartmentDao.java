package team.combinatorics.midtermproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import team.combinatorics.midtermproject.model.po.DepartmentPO;

@Repository
public interface DepartmentDao {

    int insert(@Param("dep") DepartmentPO record);

    int update(@Param("dep") DepartmentPO record);

    int deleteByPrimaryKey(Integer did);

    DepartmentPO selectByPrimaryKey(Integer did);

    List<DepartmentPO> selectAll();



}