package com.example.Innovator2025June28.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Innovator2025June28.dto.AuthRequest;
import com.example.Innovator2025June28.entity.UserInfo;
import com.example.Innovator2025June28.service.JwtService;
import com.example.Innovator2025June28.service.LoginService;

public class LoginController {
	
	
	private LoginService service;
	 
	private JwtService jwtService;
	
	 
	 
	//Error: email null first, primary key or unique constraint violation
	//this was confusing due to email nulls first but email was not null, it was unique but test case was trying to insert same email again
	@PostMapping("/signUp")
	public ResponseEntity addNewUser(UserInfo userInfo) {
		return null;
	}
	
	
	//Error: JWT strings must contain exactly 2 period characters. Found: 0
	@PostMapping("/login")
	public ResponseEntity<?> authenticateAndGetToken(AuthRequest authRequest){
		return null;
	}

}
