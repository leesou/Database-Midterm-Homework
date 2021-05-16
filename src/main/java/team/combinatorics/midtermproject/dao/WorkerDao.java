package team.combinatorics.midtermproject.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import team.combinatorics.midtermproject.model.po.WorkerPO;

@Repository
public interface WorkerDao {

    int deleteByPrimaryKey(Integer wid);


    int insert(WorkerPO record);


    WorkerPO selectByPrimaryKey(Integer wid);


    List<WorkerPO> selectAll();


    int updateByPrimaryKey(WorkerPO record);
}