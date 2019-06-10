package uca.auth.client.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uca.base.exception.GlobalExceptionHandler;
import uca.base.vo.ErrorsResp;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/10 15:59
 */
@RestControllerAdvice
@Slf4j
public class AuthClientGlobalExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(InvalidAccessTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsResp invalidAccessToken(InvalidAccessTokenException e) {
        log.error("", e);
        return ErrorsResp.newInstance("INVALID_ACCESS_TOKEN", e.getMessage());
    }


    @ExceptionHandler(InvalidUsernamePasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsResp invalidUsernamePassword(InvalidUsernamePasswordException e) {
        log.error("", e);
        return ErrorsResp.newInstance("INVALID_USERNAME_OR_PASSWORD", e.getMessage());
    }
}
