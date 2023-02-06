package com.cognifia.lms.account.security.token;

public class AuthorizeException extends RuntimeException {

    public AuthorizeException(String errorCode) {
        super(errorCode);
    }
}
