package uca.auth.client.exception;

import uca.platform.exception.StdRuntimeException;

/**
 * Created by @author andy
 * On @date 19-1-21 下午11:31
 */
public class InternalServerException extends StdRuntimeException {

    public InternalServerException(String message) {
        super(message);
    }

}
