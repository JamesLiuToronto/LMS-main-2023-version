package com.cognifia.lms.account.security.token;

public class TokenInvalidException extends Exception{

    static String MESSAGE = "Token Invalid Exception, Please check with Admin" ;
    public TokenInvalidException(String errorMessage) {
        super(errorMessage);
    }
}
