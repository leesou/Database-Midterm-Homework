package team.combinatorics.midtermproject.dao;

import java.util.List;
import java.util.Map;

import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import team.combinatorics.midtermproject.model.po.ProductPO;
import team.combinatorics.midtermproject.model.po.UserPO;

@Repository
public interface ProductDao {

    int insert(@Param("product") ProductPO record);

    int update(@Param("product") ProductPO record);

    int deleteByPrimaryKey(Integer pid);

    int deleteByUid(Integer uid);

    ProductPO selectByPrimaryKey(Integer pid);

    List<ProductPO> selectByUid(Integer uid);

    Integer countByUid(Integer uid);

    Double sumMoneyByUid(Integer uid);

    List<ProductPO> selectAll();

    List<Map> sumAllMoney();
}