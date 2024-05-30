package com.example.BirthCertificate.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public String meExceptionHandlerMethod1(){
        return "Invalid Credentials from Global Exception Handler!!";
    }
}
