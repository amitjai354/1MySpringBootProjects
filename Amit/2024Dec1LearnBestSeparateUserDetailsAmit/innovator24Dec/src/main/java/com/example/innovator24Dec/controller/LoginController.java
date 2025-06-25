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
	LoginService loginService;//this is for calling addUser in signUp not for userDetailsService
	//userDetailsService is in UserInfodetailsService class
	
	/*
	@Test
	void c_testFailedLoginAttempt() throws Exception {
		//wrong login attempt
		AuthRequest loginData = new AuthRequest("designerOne", "wrong password");
		mockMvc.perform(post("/login")
				.content(toJson(loginData)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest()).andReturn();
		//in log api if authentication manager does not authenticate.. return bad request
		//so below login api must return response entity instead of throwing error
		//if throw error from below login api then tyest case fail that bad request status not match
	}
	*/
	
	
	
	//they had not given signUp in controller, so 404 not found.. 
	//had to create by myself in LoginController,, but was given addUser() in LoginService
	@PostMapping("/signUp")  //localhost:8080/signUp
	public ResponseEntity addUser(@RequestBody UserInfo userInfo){
		//public ResponseEntity<Object> addUser(@RequestBody UserInfo userInfo){
		//if writting this giving error as in loginService given ResponseEntity<UserInfo> in exam
		//so either write userInfo or do not write anything, 
		//working with <?> as well
		return loginService.addUser(userInfo);
	}
	
	
	
	//on running these test cases one log for bad credentials always even if all test cases passed.. 
	//as we have some failed test cases also where it should throw error.. like checkFailedLogin() test case
	//so this is throwing error..
	//so this bad credentials is correct as we are pring e.print stack trace in case some error occurs
	
	
	
	
	//localhost:8080/login
	//200
	//400 Invalid credentials!
	@PostMapping("/login")
	public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest){
		//throw new BadCredentialsException("Invalid username or password"+e.getMessage());
		//in test case expecting 400.. i had written throw new.. so test case was failing
		//return type is response entity so return response entity only
		
		//if do not write @RequestBody, authRequest is null, so on debug sach me BadCredentials Exception dega.
		//in this case ya to Request Body nhi lagaya ya UserDetails me return nhi kiya username and password
		
		//if write only one catch block : Exception e, on hover on e in debug, will give BadCredentials
		
		UsernamePasswordAuthenticationToken authToken = new 
				UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
		
		try {
			authenticationManager.authenticate(authToken);
		}
//		catch (BadCredentialsException e) {
//			e.printStackTrace(); //with this very long read message written on log screen even when test case pass for login failed test case
//			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Invalid credentials!"+ e.getMessage());
//		}
		catch (Exception e) {
			e.printStackTrace();//best for debug just hover on e and do not write multiple catch blocks
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Invalid credentials!");
		}
		/*
		Most imp: Always write e.printStackTrace in all the catch Block, best for debug just hover on e
		also do not write multiple catch block, just write one Excpetion e, it will itself tell like BadCredential when you hover on e
		
		Most imp thing is: when after error, control goes to catch Block, hover mouse over e.printStacktrace mainly e
		it will give the error just in the debug screen..
		earlier after control went to catch block.. i used to look the whole log, not the test case one but the main one..
		as when we run test, one main overall log generates and one log for each test case
		 */
		
		String token = jwtService.generateToken(authRequest.getUsername());
		JwtResponse jwtResponse = new JwtResponse(token, 200);
		
		return ResponseEntity.status(HttpServletResponse.SC_OK).body(jwtResponse);
	
	}
}
