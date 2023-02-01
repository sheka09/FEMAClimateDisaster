package io.fema.error;

public class DisasterNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public DisasterNotFoundException() {
        super();
    }

    public DisasterNotFoundException(String message) {
        super(message);
    }

    public DisasterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DisasterNotFoundException(Throwable cause) {
        super(cause);
    }

    protected DisasterNotFoundException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

