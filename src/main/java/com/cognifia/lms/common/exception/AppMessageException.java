package com.cognifia.lms.common.exception;

public class AppMessageException extends RuntimeException {
    public AppMessageException() {
        super();
    }
    public AppMessageException(String message, Throwable cause) {
        super(message, cause);
    }
    public AppMessageException(String message) {
        super(message);
    }
    public AppMessageException(Throwable cause) {
        super(cause);
    }
}