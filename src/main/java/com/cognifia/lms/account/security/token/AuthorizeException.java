package com.cognifia.lms.account.security.token;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthorizeException extends RuntimeException {

    public AuthorizeException(String errorCode) {
        super(errorCode);
    }
}
