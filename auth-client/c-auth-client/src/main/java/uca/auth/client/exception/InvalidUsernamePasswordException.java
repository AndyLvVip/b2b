package uca.auth.client.exception;

import uca.platform.exception.StdRuntimeException;

public class InvalidUsernamePasswordException extends StdRuntimeException {

    public InvalidUsernamePasswordException(String message) {
        super(message);
    }

}
