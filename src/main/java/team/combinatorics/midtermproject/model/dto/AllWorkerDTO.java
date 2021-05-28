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
public class AllWorkerDTO {
    private List<WorkerDTO> allWorker;
    private Integer allWorkerNum;
    private Double allWorkerAvgSalary;
}
