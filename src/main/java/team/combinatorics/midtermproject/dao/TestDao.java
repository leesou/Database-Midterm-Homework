package team.combinatorics.midtermproject.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import team.combinatorics.midtermproject.model.po.TestPO;

@Repository
public interface TestDao {

    int deleteByPrimaryKey(Integer id);


    int insert(TestPO record);


    TestPO selectByPrimaryKey(Integer id);


    List<TestPO> selectAll();


    int updateByPrimaryKey(TestPO record);
}