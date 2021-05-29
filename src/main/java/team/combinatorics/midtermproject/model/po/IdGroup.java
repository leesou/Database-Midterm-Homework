package team.combinatorics.midtermproject.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IdGroup {

    private Integer oldId;
    private Integer newId;

}
