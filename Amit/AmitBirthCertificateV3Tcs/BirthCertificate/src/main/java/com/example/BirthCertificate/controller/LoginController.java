package com.example.BirthCertificate.controller;

import com.example.BirthCertificate.dto.JwtResponse;
import com.example.BirthCertificate.dto.LoginDTO;
import com.example.BirthCertificate.security.JWTUtill;
import com.example.BirthCertificate.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private LoginService loginService;

    @Autowired
    private JWTUtill jwtUtill;

    @PostMapping("/login")
    public Object authenticateUser(@RequestBody LoginDTO authenticationRequest){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        try{
            authenticationManager.authenticate(authenticationToken);
        }
        catch (BadCredentialsException e){
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        UserDetails userDetails = loginService.loadUserByUsername(authenticationRequest.getEmail());
        String token = jwtUtill.generateToken(userDetails);

        JwtResponse jwtResponse = new JwtResponse(token, 200);
        //return ResponseEntity.status(HttpServletResponse.SC_OK).body(jwtResponse);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
}
