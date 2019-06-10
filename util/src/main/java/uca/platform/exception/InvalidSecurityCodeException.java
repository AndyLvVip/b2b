package uca.platform.exception;

/**
 * Description:
 *
 * @author andy.lv
 * @date 2019/6/10 16:20
 */
public class InvalidSecurityCodeException extends StdRuntimeException {
    public InvalidSecurityCodeException(String message) {
        super(message);
    }

    public InvalidSecurityCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidSecurityCodeException(Throwable cause) {
        super(cause);
    }
}
