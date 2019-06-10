package uca.base.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import uca.base.vo.ErrorsResp;
import uca.platform.exception.InternalServerException;
import uca.platform.exception.InvalidSecurityCodeException;
import uca.platform.exception.StdFileNotFoundException;

import java.util.stream.Collectors;

/**
 * Created by @author andy
 * On @date 19-1-21 下午11:29
 */
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsResp illegalArgument(IllegalArgumentException e) {
        log.error("", e);
        return ErrorsResp.newInstance4IllegalArgument(e.getMessage());
    }


    @ExceptionHandler(InvalidSecurityCodeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsResp invalidSecurityCode(InvalidSecurityCodeException e) {
        log.error("", e);
        return ErrorsResp.newInstance4IllegalArgument(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorsResp methodArgumentNotValid(MethodArgumentNotValidException e) {
        log.error("", e);
        return ErrorsResp.newInstance(ErrorsResp.ILLEGAL_ARGUMENT, e.getBindingResult().getAllErrors().stream().map(ObjectError::toString).collect(Collectors.toList()));
    }

    @ExceptionHandler(StdFileNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorsResp fileNotFound(StdFileNotFoundException e) {
        log.error("", e);
        return ErrorsResp.newInstance(ErrorsResp.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsResp internalServerError(InternalServerException e) {
        log.error("", e);
        return ErrorsResp.newInstance4InternalServerError(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorsResp internalServerError(Exception e) {
        log.error("", e);
        return ErrorsResp.newInstance4InternalServerError(e.getMessage());
    }
}
