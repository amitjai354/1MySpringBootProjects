package org.example.controllers;

import org.example.model.JwtRequest;
import org.example.model.JwtResponse;
import org.example.security.JwtHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest){
        this.doAuthenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtHelper.generateToken(userDetails);

        JwtResponse jwtResponse = JwtResponse.builder()
                .jwtToken(token)
                .userName(userDetails.getUsername())
                .build();
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

    }

    private void doAuthenticate(String email, String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authenticationToken);
        }
        catch(BadCredentialsException e){
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    private String exceptionHandler(){
        return "Credentials Invalid !!";
    }
}
