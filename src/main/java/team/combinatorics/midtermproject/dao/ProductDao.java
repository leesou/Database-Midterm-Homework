package team.combinatorics.midtermproject.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import team.combinatorics.midtermproject.model.po.ProductPO;

@Repository
public interface ProductDao {

    int deleteByPrimaryKey(Integer pid);


    int insert(ProductPO record);


    ProductPO selectByPrimaryKey(Integer pid);


    List<ProductPO> selectAll();


    int updateByPrimaryKey(ProductPO record);
}