package uca.auth.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by @author andy
 * On @date 19-1-21 下午11:29
 */
@Component
public class Handler {

    @ExceptionHandler()
    public ResponseEntity<String> internalServerError() {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
