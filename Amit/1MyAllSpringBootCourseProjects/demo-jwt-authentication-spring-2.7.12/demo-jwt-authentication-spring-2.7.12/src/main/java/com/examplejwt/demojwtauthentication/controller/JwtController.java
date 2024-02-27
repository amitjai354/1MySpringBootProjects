package com.examplejwt.demojwtauthentication.controller;

import com.examplejwt.demojwtauthentication.jwtUtilHelper.JwtUtil;
import com.examplejwt.demojwtauthentication.model.JwtRequest;
import com.examplejwt.demojwtauthentication.model.JwtResponse;
import com.examplejwt.demojwtauthentication.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {
    @Autowired
    private AuthenticationManager authenticationManager;
    //to authenticate

    @Autowired
    private MyUserDetailService myUserDetailService;

    @Autowired
    private JwtUtil jwtUtil;
	
	
	//ResponseEntity represents the whole HTTP response: status code, headers, and body. As a result, we can use it to fully configure the HTTP response. 
	//If we want to use it, we have to return it from the endpoint; Spring takes care of the rest.
	
    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println(jwtRequest);
        //first of all we will authenticate username and password
        try{
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(), jwtRequest.getPassword()));
        }catch (UsernameNotFoundException e){
            e.printStackTrace();//print on console.. e.getMessage()
            throw new Exception("Bad Credentials");
        }catch (BadCredentialsException e){
            e.printStackTrace();//print on console.. e.getMessage()
            throw new Exception("Bad Credentials");
        }

        UserDetails userDetails=this.myUserDetailService.loadUserByUsername(jwtRequest.getUsername());
        //we need this userdetails to generate token.. inside jwtUtil
        //public String generateToken(UserDetails userDetails) {

        String token = this.jwtUtil.generateToken(userDetails);
        System.out.println("Jwt token: "+token);

        //we have to return token as json response
        //{"token":"value"}
        return ResponseEntity.ok(new JwtResponse(token));//staus 200 ok
        //"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhbWl0IiwiZXhwIjoxNjg0ODk4Njg2LCJpYXQiOjE2ODQ4NjI2ODZ9.QHAZRZfJ0veJh8GvdJKWXruGuf-bQDF0u2R7ma8x4iw"
    }
}
