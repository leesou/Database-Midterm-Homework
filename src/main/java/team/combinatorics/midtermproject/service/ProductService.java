package team.combinatorics.midtermproject.service;

import team.combinatorics.midtermproject.model.dto.AllProductDTO;
import team.combinatorics.midtermproject.model.dto.ProductDTO;

import java.util.List;
import java.util.Map;

public interface ProductService {

    Integer addNewProduct(ProductDTO productDTO);

    void updateProduct(ProductDTO productDTO);

    void deleteProduct(Integer pid);

    ProductDTO getProductByPid(Integer pid);

    List<ProductDTO> getAllProduct();

    List<Map> getAllMoneyByUid();

    AllProductDTO getProductByUid(Integer uid);

}
