package team.combinatorics.midtermproject.controller;

import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;
import team.combinatorics.midtermproject.model.dto.AllProductDTO;
import team.combinatorics.midtermproject.model.dto.CommonResult;
import team.combinatorics.midtermproject.model.dto.ProductDTO;
import team.combinatorics.midtermproject.service.ProductService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    /**
     * 添加product
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public CommonResult<Integer> addNewProduct(
            @RequestBody @NotNull(message = "商品信息不能为空")ProductDTO productDTO
            ) {
        Integer pid = productService.addNewProduct(productDTO);
        return new CommonResult<>(200, "添加成功", pid);
    }

    /**
     * 更新product信息
     */
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public CommonResult<String> updateProduct(
            @RequestBody @NotNull(message = "商品信息不能为空")ProductDTO productDTO
    ) {
        productService.updateProduct(productDTO);
        return new CommonResult<>(200, "更新成功", "Product information has been changed.");
    }

    /**
     * 删除product
     */
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public CommonResult<String> deleteProduct(
            @RequestParam @NotNull(message = "商品id不能为空")Integer pid
    ) {
        productService.deleteProduct(pid);
        return new CommonResult<>(200, "删除成功", "Product has been deleted.");
    }

    /**
     * 查询单个product的信息
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CommonResult<ProductDTO> getOneProduct(
            @RequestParam @NotNull(message = "商品id不能为空")Integer pid
    ) {
        return new CommonResult<>(200, "查询成功", productService.getProductByPid(pid));
    }

    /**
     * 查询单个user买了哪些product，及总金额
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public CommonResult<AllProductDTO> getProductByUser(
            @RequestParam @NotNull(message = "用户id不能为空")Integer uid
    ) {
        return new CommonResult<>(200, "查询成功", productService.getProductByUid(uid));
    }

    /**
     * 查所有product
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public CommonResult<List<ProductDTO>> getAllProduct() {
        return new CommonResult<>(200, "查询成功", productService.getAllProduct());
    }

    /**
     * 列出所有用户的购买总金额
     */
    @RequestMapping(value = "/money", method = RequestMethod.GET)
    public CommonResult<List<Map>> getAllMoneyByUser() {
        return new CommonResult<>(200, "查询成功", productService.getAllMoneyByUid());
    }

}
