package controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;

import config.UserInfoUserDetailsService;
import dto.AuthRequest;
import entity.UserInfo;
import repository.UserInfoRepository;
import service.JwtService;
import service.LoginService;


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
