package team.combinatorics.midtermproject.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  ErrorInfoEnum {
    ;

    private final Integer errCode;
    private final String errMsg;
}
