package team.combinatorics.midtermproject.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import team.combinatorics.midtermproject.model.po.ServicePO;

@Repository
public interface ServiceDao {

    int deleteByPrimaryKey(Integer sid);


    int insert(ServicePO record);


    ServicePO selectByPrimaryKey(Integer sid);


    List<ServicePO> selectAll();


    int updateByPrimaryKey(ServicePO record);
}