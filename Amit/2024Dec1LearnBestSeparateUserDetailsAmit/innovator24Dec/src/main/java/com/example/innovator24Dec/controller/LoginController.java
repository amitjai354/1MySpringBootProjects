package com.example.innovator24Dec.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.innovator24Dec.dto.AuthRequest;
import com.example.innovator24Dec.service.JwtService;

public class LoginController {

	private JwtService jwtService;
	
	private AuthenticationManager authenticationManager;
	
	/*
	@Test
	void c_testFailedLoginAttempt() throws Exception {
		//wrong login attempt
		AuthRequest loginData = new AuthRequest("designerOne", "wrong password");
		mockMvc.perform(post("/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
		//in log api if authentication mnager does not authenticare.. return bad request
	}
	*/
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateAndGetToken(AuthRequest authRequest){
		return null;
	}
}
