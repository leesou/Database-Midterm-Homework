package team.combinatorics.midtermproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import team.combinatorics.midtermproject.model.po.UserPO;
import team.combinatorics.midtermproject.model.po.WorkerPO;

@Repository
public interface UserDao {
    int insert(@Param("user") UserPO record); 

    int update(@Param("user") UserPO record);

    int deleteByPrimaryKey(Integer uid);

    UserPO selectByPrimaryKey(Integer uid);

    List<UserPO> selectAll();
}