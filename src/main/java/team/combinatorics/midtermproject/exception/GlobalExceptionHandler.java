package team.combinatorics.midtermproject.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import team.combinatorics.midtermproject.model.dto.CommonResult;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = KnownException.class)
    public CommonResult<String> knownExceptionHandler(KnownException se) {
        System.out.println("错误" + se.getErrCode() + ": " + se.getMessage());
        return new CommonResult<>(se.getErrCode(), "Error", se.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public CommonResult<Map<String, Object>> defaultExceptionHandler(Exception e) {
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("errMsg", e.getMessage());
        returnMap.put("errStackTrace", e.getStackTrace());
        System.out.println(e.getMessage());

        return new CommonResult<>(40000, "Unknown Error", returnMap);
    }
}
