package uca.platform.exception;

/**
 * Created by andy.lv
 * on: 2019/1/25 16:52
 */
public class InternalServerException extends StdRuntimeException {
    public InternalServerException(String message) {
        super(message);
    }

    public InternalServerException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalServerException(Throwable cause) {
        super(cause);
    }
}
