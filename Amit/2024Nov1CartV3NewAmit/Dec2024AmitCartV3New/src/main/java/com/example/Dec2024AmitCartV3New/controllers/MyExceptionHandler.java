package com.example.Dec2024AmitCartV3New.controllers;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

	@ExceptionHandler(BadCredentialsException.class)
	public String myExceptionHandler1() {
		return "invalid username or password from Global Exception";
	}
}
