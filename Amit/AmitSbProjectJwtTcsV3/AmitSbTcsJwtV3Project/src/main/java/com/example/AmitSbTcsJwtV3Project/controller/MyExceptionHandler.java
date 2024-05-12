package com.example.AmitSbTcsJwtV3Project.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler(){
        return "Invalid Crdentials";
        //earlier before this code, error only in console and in Postman only 400 bad request but no msg that what error
        //How will customer what is 400 and what is error
        //after this, now pring Invalid Crdentials in Postman also
    }

//    @ExceptionHandler(UsernameNotFoundException.class)
//    public String exceptionHandler1(){
//        return "Invalid Username";
//        //earlier before this code, error only in console and in Postman only 400 bad request but no msg that what error
//        //How will customer what is 400 and what is error
//        //after this, now pring Invalid Crdentials in Postman also
//    }
}
