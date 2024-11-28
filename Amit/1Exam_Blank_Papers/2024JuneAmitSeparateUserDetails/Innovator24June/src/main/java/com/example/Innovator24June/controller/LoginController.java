package com.example.Innovator24June.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Innovator24June.config.UserInfoUserDetailsService;
import com.example.Innovator24June.dto.AuthRequest;
import com.example.Innovator24June.entity.UserInfo;
import com.example.Innovator24June.repository.UserInfoRepository;
import com.example.Innovator24June.service.JwtService;






public class LoginController {
	
	//private LoginService loginService;//added by me
	
	private UserInfoUserDetailsService userDetailsService;
	
	private JwtService jwtService;
	
	private AuthenticationManager authenticationManager;
	
	private UserInfoRepository userInfoRepository;
	
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/signUp")
	public ResponseEntity addNewUser(UserInfo userInfo) {
		return null;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateAndGetToken(AuthRequest authRequest){
		return null;
	}

}
