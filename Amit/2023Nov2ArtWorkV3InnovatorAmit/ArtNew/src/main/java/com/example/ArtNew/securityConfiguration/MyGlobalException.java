package com.example.ArtNew.securityConfiguration;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyGlobalException {

    @ExceptionHandler(BadCredentialsException.class)
    public String myExceptionHandlerMethod1(){
        return "Invalid Credentials from Global Exception";
    }
}
