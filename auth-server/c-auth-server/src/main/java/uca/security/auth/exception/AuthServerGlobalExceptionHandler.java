package uca.security.auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import uca.base.exception.GlobalExceptionHandler;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/10 17:13
 */

@RestControllerAdvice
@Slf4j
public class AuthServerGlobalExceptionHandler extends GlobalExceptionHandler {

}
