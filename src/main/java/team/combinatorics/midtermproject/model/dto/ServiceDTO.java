package team.combinatorics.midtermproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDTO {

    private Integer sid;
    private String serviceType;
    private String description;
    private String state;
    private Integer wid;
    private Integer pid;

}
