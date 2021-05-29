package team.combinatorics.midtermproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import team.combinatorics.midtermproject.model.po.WorkerPO;

@Repository
public interface WorkerDao {

    int insert(@Param("worker") WorkerPO record);

    int update(@Param("worker") WorkerPO record);

    int deleteByPrimaryKey(Integer wid);

    int deleteByDid(Integer did);

    WorkerPO selectByPrimaryKey(Integer wid);

    List<WorkerPO> selectByDid(Integer did);

    List<WorkerPO> selectAll();

    Integer countByDid(Integer did);

    Integer countAll();

    Double calculateAvgSalaryByDid(Integer did);

    Double calculateAllAvgSalary();

}