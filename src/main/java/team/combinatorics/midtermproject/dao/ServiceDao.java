package team.combinatorics.midtermproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import team.combinatorics.midtermproject.model.po.ServicePO;
import team.combinatorics.midtermproject.model.po.UserPO;

@Repository
public interface ServiceDao {
    int insert(@Param("service") ServicePO record);

    int update(@Param("service") ServicePO record);

    int deleteByPrimaryKey(Integer uid);

    ServicePO selectByPrimaryKey(Integer uid);

    List<ServicePO> selectAll();

    List<ServicePO> selectByUid(Integer uid);

    int countByUid(Integer uid);

    List<ServicePO> selectByWid(Integer wid);

    int countByWid(Integer wid);

    List<ServicePO> selectByDid(Integer did);

    int countByDid(Integer did);
}