package com.example.innovator24Dec.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.innovator24Dec.dto.AuthRequest;
import com.example.innovator24Dec.dto.JwtResponse;
import com.example.innovator24Dec.entity.UserInfo;
import com.example.innovator24Dec.service.JwtService;
import com.example.innovator24Dec.service.LoginService;

import jakarta.servlet.http.HttpServletResponse;


//i missed @RestController so all apis failing as api not found error
//so all test cases failing as login not working so no token generated so no api work
@RestController
public class LoginController {

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	LoginService loginService;
	
	/*
	@Test
	void c_testFailedLoginAttempt() throws Exception {
		//wrong login attempt
		AuthRequest loginData = new AuthRequest("designerOne", "wrong password");
		mockMvc.perform(post("/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
		//in log api if authentication mnager does not authenticare.. return bad request
		//so below login api must return response entity instead of throwing error
		//if throw error from below login api then tyest case fail that bad request status not match
	}
	*/
	
	
	
	//they had not given signUp in controller, so 404 not found.. 
	//had to create by myself in LoginController,, but was given addUser() in LoginService
	@PostMapping("/signUp")
	public ResponseEntity<UserInfo> addUser(@RequestBody UserInfo userInfo) throws Exception{
		return loginService.addUser(userInfo);
	}
	
	
	//on running these test cases one log for bad credentials always even if all test cases passed.. 
	//as we have some failed test cases also where it should throw error.. like checkFailedLogin() test case
	//so this is throwing error..
	//so this bad credentials is correct as we are printing e.print stack trace in case some error occurs
	@PostMapping("/login")
	public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest){
		//throw new BadCredentialsException("Invalid username or password"+e.getMessage());
		//in test case expecting 400.. i had written throw new.. so test case was failing
		//return type is response entity so return response entity only
		
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
		try {
			authenticationManager.authenticate(authToken);
		}
		catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in login" + e.getMessage());
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in login" +e.getMessage());
		}
		
		String token = jwtService.generateToken(authRequest.getUsername());
		JwtResponse jwtResponse = new JwtResponse(token, 200);
		
		return ResponseEntity.status(HttpServletResponse.SC_OK).body(jwtResponse);
	}
}
