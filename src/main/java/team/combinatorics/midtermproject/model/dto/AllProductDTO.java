package team.combinatorics.midtermproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllProductDTO {

    List<ProductDTO> allProduct;
    Integer allProductNum;
    Double allProductMoney;

}
