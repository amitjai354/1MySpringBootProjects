package com.example.demoTcsArtWorkNov23Innovator.controller;

import com.example.demoTcsArtWorkNov23Innovator.dto.JwtResponse;
import com.example.demoTcsArtWorkNov23Innovator.dto.LoginDTO;
import com.example.demoTcsArtWorkNov23Innovator.model.UserModel;
import com.example.demoTcsArtWorkNov23Innovator.security.JWTUtil;
import com.example.demoTcsArtWorkNov23Innovator.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {
    
	@Autowired
    private AuthenticationManager authenticationManager;

 
	@Autowired
    LoginService loginService;

	@Autowired
    private JWTUtil jwtUtil;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@PostMapping("/signUp")
	public ResponseEntity<UserModel> addUser(@RequestBody UserModel userModel) throws Exception{
		try {
			userModel.setPassword(passwordEncoder.encode(userModel.getPassword()));
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(userModel);
		}
		catch (Exception e) {
			//return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new UserModel());
			throw new Exception("error in sign up");
		}
		
	}

    @PostMapping("/login")
    public Object authenticateUser(@RequestBody LoginDTO authenticationRequest){
        //we have to return token in output after taking username password in input here
        //since passing username password to verfify user and generate tokenin jwt request so use UsenamePaswordAuthenticatiionToken
        // and pass username , password in it
        //then authticate useing authenticationManager bean that we created
    	
        //org.springframework.security.authentication.InternalAuthenticationServiceException:
        // UserDetailsService returned null, which is an interface contract violation
        //this exception is thrown if username is wrong while trying to generate token
        //if password wrong then BadCredentials exception

        //Spring Security hides the username does not exist exception by wrapping it so that when developing,
        // developers only get BadCredentialsException
        //when we throw username not found exception then Spring wraps it and returns Bad credential
        //as if we say username not found , hacker will know surely that he need to change username only
        //if (userModel==null){
        //  throw new UsernameNotFoundException("Username not found");
        //}
        //when returning username not found we are getting 500 bad request:  in load user by username code
    	
    	UsernamePasswordAuthenticationToken authenticationToken = 
    			new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getEmail());
    	try {
    		authenticationManager.authenticate(authenticationToken);
    	}
    	catch (BadCredentialsException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in login");
		}
    	catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in login");
		}
    	
    	UserDetails userDetails = loginService.loadUserByUsername(authenticationRequest.getEmail());
    	String token = jwtUtil.generateToken(userDetails);
    	
    	JwtResponse jwtResponse = new JwtResponse(token, 200);
    	return ResponseEntity.status(HttpServletResponse.SC_OK).body(jwtResponse);
    
    }
}
