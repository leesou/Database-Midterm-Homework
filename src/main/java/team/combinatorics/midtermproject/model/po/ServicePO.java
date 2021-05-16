package team.combinatorics.midtermproject.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServicePO {

    private Integer sid;
    private String serviceType;
    private String description;
    private String state;
    private Integer wid;
    private Integer pid;

}