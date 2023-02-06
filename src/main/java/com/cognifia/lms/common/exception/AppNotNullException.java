package com.cognifia.lms.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class AppNotNullException extends RuntimeException {
    public AppNotNullException() {
        super();
    }
    public AppNotNullException(String message, Throwable cause) {
        super(message, cause);
    }
    public AppNotNullException(String message) {
        super(message);
    }
    public AppNotNullException(Throwable cause) {
        super(cause);
    }
}