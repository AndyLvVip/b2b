package uca.base.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by andy.lv
 * on: 2019/3/18 11:42
 */
@Data
public class ErrorsResp {

    private List<ErrorVo> errors;

    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";

    public static final String ICBC_SERVER_ERROR = "ICBC_SERVER_ERROR";

    public static final String ILLEGAL_ARGUMENT = "ILLEGAL_ARGUMENT";

    public static final String INVALID_SECURITY_CODE = "INVALID_SECURITY_CODE";

    public static final String NOT_FOUND = "NOT_FOUND";

    @Data
    @AllArgsConstructor
    public static class ErrorVo {
        private String code;
        private String message;
    }

    public static ErrorsResp newInstance(String code, String message) {
        return newInstance(Arrays.asList(new ErrorVo(code, message)));
    }

    public static ErrorsResp newInstance(String code, String[] messages) {
        List<ErrorVo> errors = new ArrayList<>();
        for(String msg : messages) {
            ErrorVo vo = new ErrorVo(code, msg);
            errors.add(vo);
        }
        return newInstance(errors);
    }

    public static ErrorsResp newInstance(String code, List<String> messages) {
        return newInstance(messages.stream().map(m -> new ErrorVo(code, m)).collect(Collectors.toList()));
    }

    public static ErrorsResp newInstance(List<ErrorVo> errors) {
        ErrorsResp resp = new ErrorsResp();
        resp.setErrors(errors);
        return resp;
    }

    public static ErrorsResp newInstance4InternalServerError(String message) {
        return newInstance(INTERNAL_SERVER_ERROR, message);
    }

    public static ErrorsResp newInstance4IllegalArgument(String message) {
        return newInstance(ILLEGAL_ARGUMENT, message);
    }

    public static ErrorsResp newInstance4InvalidSecurityCode(String message) {
        return newInstance(INVALID_SECURITY_CODE, message);
    }
}
