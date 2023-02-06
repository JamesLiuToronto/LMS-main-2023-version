package com.cognifia.lms.account.security.token;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NON_AUTHORITATIVE_INFORMATION)
public class AppNoAccessException extends RuntimeException {
    public AppNoAccessException() {
        super();
    }
    public AppNoAccessException(String message, Throwable cause) {
        super(message, cause);
    }
    public AppNoAccessException(String message) {
        super(message);
    }
    public AppNoAccessException(Throwable cause) {
        super(cause);
    }
}