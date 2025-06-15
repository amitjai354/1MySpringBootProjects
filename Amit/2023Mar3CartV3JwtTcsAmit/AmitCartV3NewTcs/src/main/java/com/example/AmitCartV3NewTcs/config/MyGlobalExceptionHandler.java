package com.example.AmitCartV3NewTcs.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyGlobalExceptionHandler {
	
	@ExceptionHandler(BadCredentialsException.class)
	public String myExceptionHandlerMethod1() {
		return "Invalid Credentials Global ExceptionHandler";
	}
}
