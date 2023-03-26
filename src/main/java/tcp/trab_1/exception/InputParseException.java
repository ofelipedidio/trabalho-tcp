package tcp.trab_1.exception;

public class InputParseException extends RuntimeException {
    public InputParseException() {
    }

    public InputParseException(String message) {
        super(message);
    }

    public InputParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public InputParseException(Throwable cause) {
        super(cause);
    }

    protected InputParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
