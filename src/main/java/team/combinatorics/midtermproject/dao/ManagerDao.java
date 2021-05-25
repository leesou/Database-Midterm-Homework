package team.combinatorics.midtermproject.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import team.combinatorics.midtermproject.model.po.ManagerPO;

@Repository
public interface ManagerDao {

    int insertManager(@Param("manager") ManagerPO record);

    int updateManager(@Param("manager") ManagerPO record);

    int deleteManager(Integer did);

}
