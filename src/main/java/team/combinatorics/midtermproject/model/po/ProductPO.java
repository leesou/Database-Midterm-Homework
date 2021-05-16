package team.combinatorics.midtermproject.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPO {

    private Integer pid;
    private String productName;
    private Double price;
    private Date sellTime;
    private Integer duration;
    private Integer uid;

}