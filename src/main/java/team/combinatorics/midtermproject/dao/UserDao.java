package team.combinatorics.midtermproject.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import team.combinatorics.midtermproject.model.po.UserPO;

@Repository
public interface UserDao {

    int deleteByPrimaryKey(Integer uid);


    int insert(UserPO record);


    UserPO selectByPrimaryKey(Integer uid);


    List<UserPO> selectAll();


    int updateByPrimaryKey(UserPO record);
}