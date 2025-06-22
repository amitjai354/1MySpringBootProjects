package com.example.Dec2024AmitCartV3New.controllers;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyExceptionHandler {

	@ExceptionHandler(BadCredentialsException.class)
	public String exceptionHandler1() {
		return "exception from global exception";
	}
}
