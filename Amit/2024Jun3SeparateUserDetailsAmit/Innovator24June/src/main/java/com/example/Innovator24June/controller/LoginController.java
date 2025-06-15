package com.example.Innovator24June.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Innovator24June.config.UserInfoUserDetailsService;
import com.example.Innovator24June.dto.AuthRequest;
import com.example.Innovator24June.dto.JwtResponse;
import com.example.Innovator24June.entity.UserInfo;
import com.example.Innovator24June.repository.UserInfoRepository;
import com.example.Innovator24June.service.JwtService;
import com.example.Innovator24June.service.LoginService;

import jakarta.servlet.http.HttpServletResponse;





@RestController
public class LoginController {
	
	//private LoginService loginService;//added by me
	
	@Autowired
	private UserInfoUserDetailsService userDetailsService;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	LoginService loginService;
	
	@PostMapping("/signUp")
	public ResponseEntity addNewUser(@RequestBody UserInfo userInfo) {
		return loginService.addUser(userInfo);
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest){
		UsernamePasswordAuthenticationToken authToken = new 
				UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
		try {
			authenticationManager.authenticate(authToken);
		}
		catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("invalid username password from login controller");
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("invalid username password from login controller");
		}
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
		String token = jwtService.generateToken(userDetails.getUsername());
		JwtResponse jwtResponse = new JwtResponse(token, 200);
		return ResponseEntity.status(HttpServletResponse.SC_OK).body(jwtResponse);
	}

}
