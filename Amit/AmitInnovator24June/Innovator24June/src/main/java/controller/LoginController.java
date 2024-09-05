package controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;

import dto.AuthRequest;
import entity.UserInfo;
import service.JwtService;
import service.LoginService;

public class LoginController {
	
	private LoginService loginService;
	
	private JwtService jwtService;
	
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/signUp")
	public ResponseEntity addNewUser(UserInfo userInfo) {
		return null;
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateAndGetToken(AuthRequest authRequest){
		return null;
	}

}
