package team.combinatorics.midtermproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import team.combinatorics.midtermproject.model.po.IdGroup;
import team.combinatorics.midtermproject.model.po.ServicePO;
import team.combinatorics.midtermproject.model.po.UserPO;

@Repository
public interface ServiceDao {
    int insert(@Param("service") ServicePO record);

    int update(@Param("service") ServicePO record);

    int updateWid(IdGroup idGroup);

    int updateWidByDid(Integer did);

    int deleteByPrimaryKey(Integer sid);

    int deleteByUid(Integer uid);

    int deleteByPid(Integer pid);

    ServicePO selectByPrimaryKey(Integer sid);

    List<ServicePO> selectAll();

    int countAll();

    List<ServicePO> selectByUid(Integer uid);

    int countByUid(Integer uid);

    List<ServicePO> selectByWid(Integer wid);

    int countByWid(Integer wid);

    List<ServicePO> selectByDid(Integer did);

    int countByDid(Integer did);
}