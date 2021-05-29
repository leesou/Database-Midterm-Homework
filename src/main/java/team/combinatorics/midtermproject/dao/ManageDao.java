package team.combinatorics.midtermproject.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import team.combinatorics.midtermproject.model.po.ManagePO;

@Repository
public interface ManageDao {

    int insertManager(@Param("manager") ManagePO record);

    int updateManager(@Param("manager") ManagePO record);

    int deleteManager(Integer did);

    int deleteManagerByWid(Integer wid);

    ManagePO selectByDid(Integer did);

    List<ManagePO> selectAll();

    Integer countAll();

}