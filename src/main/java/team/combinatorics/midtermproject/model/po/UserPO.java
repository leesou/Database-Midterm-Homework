package team.combinatorics.midtermproject.model.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserPO {

    private Integer uid;
    private String userName;
    private String address;
    private String phoneNumber;
    private String email;

}