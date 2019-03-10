package uca.auth.client.exception;

import uca.platform.exception.StdRuntimeException;

public class InvalidAccessTokenException extends StdRuntimeException {
    public InvalidAccessTokenException(String message) {
        super(message);
    }
}
