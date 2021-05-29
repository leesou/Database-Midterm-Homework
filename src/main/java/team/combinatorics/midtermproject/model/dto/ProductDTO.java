package team.combinatorics.midtermproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Integer pid;
    private String productName;
    private Double price;
    private Date sellTime;
    private Integer duration;
    private Integer uid;

}
