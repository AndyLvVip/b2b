package uca.platform.exception;

/**
 * Created by andy.lv
 * on: 2019/1/25 16:51
 */
public class StdRuntimeException extends RuntimeException {
    public StdRuntimeException(String message) {
        super(message);
    }

    public StdRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public StdRuntimeException(Throwable cause) {
        super(cause);
    }
}
