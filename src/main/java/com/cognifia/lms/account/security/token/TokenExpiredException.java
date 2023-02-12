package com.cognifia.lms.account.security.token;

public class TokenExpiredException extends AuthorizeException{

    static String MESSAGE = "Token has been expired !" ;
    public TokenExpiredException(String errorMessage) {
        super(errorMessage);
    }
}
