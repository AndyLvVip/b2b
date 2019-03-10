package uca.auth.client.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Created by @author andy
 * On @date 19-1-21 下午11:29
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String internalServerError(InternalServerException ex) {
        log.error("", ex);
        return "服务器内部异常，如有问题，请联系管理员";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String illegalArgument(IllegalArgumentException ex) {
        log.error("", ex);
        return ex.getMessage();
    }

    @ExceptionHandler(InvalidUsernamePasswordException.class)
    public String invalidUsernamePassword(InvalidUsernamePasswordException ex) {
        log.error("", ex);
        return ex.getMessage();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String exception(Exception ex) {
        log.error("", ex);
        return "服务器内部异常，如有问题，请联系管理员";
    }

    @ExceptionHandler(InvalidAccessTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidAccessToken(InvalidAccessTokenException ex) {
        log.error("", ex);
        return "无效的access_token";
    }
}
