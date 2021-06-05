package team.combinatorics.midtermproject.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.combinatorics.midtermproject.dao.ProductDao;
import team.combinatorics.midtermproject.dao.ServiceDao;
import team.combinatorics.midtermproject.dao.UserDao;
import team.combinatorics.midtermproject.exception.ErrorInfoEnum;
import team.combinatorics.midtermproject.exception.KnownException;
import team.combinatorics.midtermproject.model.dto.AllProductDTO;
import team.combinatorics.midtermproject.model.dto.ProductDTO;
import team.combinatorics.midtermproject.model.po.ProductPO;
import team.combinatorics.midtermproject.service.ProductService;

import java.util.List;
import java.util.Map;
import java.util.Vector;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    ProductDao productDao;
    ServiceDao serviceDao;
    UserDao userDao;

    // 添加商品
    @Override
    public Integer addNewProduct(ProductDTO productDTO) {
        System.out.println("[添加商品]商品名称："+productDTO.getProductName()+"，购买人id："+productDTO.getUid());
        ProductPO productPO = ProductPO.builder().
                                productName(productDTO.getProductName()).
                                price(productDTO.getPrice()).
                                sellTime(productDTO.getSellTime()).
                                duration(productDTO.getDuration()).
                                uid(productDTO.getUid()).
                                build();
        int num = productDao.insert(productPO);
        if(num<1 || productPO.getPid()==null)
            throw new KnownException(ErrorInfoEnum.PRODUCT_INSERT_ERROR);
        return productPO.getPid();
    }

    // 更新商品信息
    @Override
    public void updateProduct(ProductDTO productDTO) {
        System.out.println("[更新商品信息]商品id："+productDTO.getPid());
        ProductPO productPO = ProductPO.builder().
                pid(productDTO.getPid()).
                productName(productDTO.getProductName()).
                price(productDTO.getPrice()).
                sellTime(productDTO.getSellTime()).
                duration(productDTO.getDuration()).
                uid(productDTO.getUid()).
                build();
        int num = productDao.update(productPO);
        if(num<1)
            throw new KnownException(ErrorInfoEnum.PRODUCT_UPDATE_ERROR);
    }

    // 删除商品
    // 要先删除service
    @Override
    @Transactional
    public void deleteProduct(Integer pid) {
        System.out.println("[删除商品]商品id："+pid.toString());

        int num_service = serviceDao.deleteByPid(pid);
        System.out.println("[删除商品]关联的售后服务数量："+num_service);

        int num = productDao.deleteByPrimaryKey(pid);
        if(num<1)
            throw new KnownException(ErrorInfoEnum.PRODUCT_DELETE_ERROR);
    }

    @Override
    public ProductDTO getProductByPid(Integer pid) {
        System.out.println("[获取单个商品信息]商品id："+pid.toString());
        ProductPO productPO = productDao.selectByPrimaryKey(pid);
        if(productPO==null)
            throw new KnownException(ErrorInfoEnum.PRODUCT_SELECT_ERROR);
        return ProductDTO.builder().
                    pid(productPO.getPid()).
                    productName(productPO.getProductName()).
                    price(productPO.getPrice()).
                    sellTime(productPO.getSellTime()).
                    duration(productPO.getDuration()).
                    uid(productPO.getUid()).
                    build();
    }

    // 获取全部商品信息
    @Override
    public List<ProductDTO> getAllProduct() {
        System.out.println("[获取全部商品信息]");

        List<ProductPO> productPOList = productDao.selectAll();
        List<ProductDTO> productDTOList = new Vector<>();
        for(ProductPO productPO:productPOList) {
            ProductDTO productDTO = ProductDTO.builder().
                    pid(productPO.getPid()).
                    productName(productPO.getProductName()).
                    price(productPO.getPrice()).
                    sellTime(productPO.getSellTime()).
                    duration(productPO.getDuration()).
                    uid(productPO.getUid()).
                    build();
            productDTOList.add(productDTO);
        }
        return productDTOList;
    }

    @Override
    public List<Map> getAllMoneyByUid() {
        System.out.println("[获取所有用户的购买总金额]");
        return productDao.sumAllMoney();
    }

    // 获取单个用户的所有商品，总金额
    @Override
    @Transactional
    public AllProductDTO getProductByUid(Integer uid) {
        System.out.println("[获取单个用户的购买情况]用户id："+uid);
        // 检查用户是否存在
        if(userDao.selectByPrimaryKey(uid)==null)
            throw new KnownException(ErrorInfoEnum.PRODUCT_SELECT_ERROR);

        List<ProductPO> productPOList = productDao.selectByUid(uid);
        List<ProductDTO> productDTOList = new Vector<>();
        for(ProductPO productPO:productPOList) {
            ProductDTO productDTO = ProductDTO.builder().
                    pid(productPO.getPid()).
                    productName(productPO.getProductName()).
                    price(productPO.getPrice()).
                    sellTime(productPO.getSellTime()).
                    duration(productPO.getDuration()).
                    uid(productPO.getUid()).
                    build();
            productDTOList.add(productDTO);
        }

        int allProductNum = productDao.countByUid(uid);
        Double allProductMoney = productDao.sumMoneyByUid(uid);
        if(allProductMoney==null)
            allProductMoney = 0.;

        return AllProductDTO.builder().
                allProduct(productDTOList).
                allProductMoney(allProductMoney).
                allProductNum(allProductNum).
                build();
    }

}
