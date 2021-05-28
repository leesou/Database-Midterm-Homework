package team.combinatorics.midtermproject.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDTO {

    private Integer wid;
    private String workerName;
    private Double salary;
    private Integer did;
    private String phoneNumber;
    private String email;

}
