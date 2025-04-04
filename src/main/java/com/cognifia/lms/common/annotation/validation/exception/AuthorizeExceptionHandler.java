package com.cognifia.lms.common.annotation.validation.exception;

import com.cognifia.lms.account.security.token.AuthorizeException;
import com.cognifia.lms.account.security.token.TokenExpiredException;
import com.cognifia.lms.common.annotation.validation.errormessage.ErrorMessage;
import com.cognifia.lms.common.exception.AppNoAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestControllerAdvice
public class AuthorizeExceptionHandler {

    @Autowired
    ErrorMessage errorMessage ;

//    @ExceptionHandler({AuthorizeException.class, TokenExpiredException.class, AppNoAccessException.class})
//    @ResponseBody
//    //public ResponseEntity<List> processUnmergeException(final MethodArgumentNotValidException ex) {
//    public ResponseEntity<List> processUnmergeException(final AuthorizeException ex){
//
//        String error = ErrorMessage.toLocale(ex.getMessage()) ;
//        ex.printStackTrace() ;
//        return new ResponseEntity<>((new ArrayList<String>(Arrays.asList(error))), HttpStatus.UNAUTHORIZED);
//    }

    @ExceptionHandler({AuthorizeException.class})
    public ResponseEntity<String> processUnmergeException(final AuthorizeException ex){
        String error = ErrorMessage.toLocale(ex.getMessage()) ;
        ex.printStackTrace() ;
        //return new ResponseEntity<>((new ArrayList<String>(Arrays.asList(error))), HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error) ;
    }

    @ExceptionHandler({TokenExpiredException.class})
    public ResponseEntity<String> processTokenExpiredException(final TokenExpiredException ex){
        String error = ErrorMessage.toLocale(ex.getMessage()) ;
        ex.printStackTrace() ;
        //return new ResponseEntity<>((new ArrayList<String>(Arrays.asList(error))), HttpStatus.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error) ;
    }





}
