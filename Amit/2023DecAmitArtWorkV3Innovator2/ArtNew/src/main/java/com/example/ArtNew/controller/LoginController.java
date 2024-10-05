package com.example.ArtNew.controller;

import com.example.ArtNew.dtoDataTransferObject.JwtRequest;
import com.example.ArtNew.dtoDataTransferObject.JwtResponse;
import com.example.ArtNew.securityConfiguration.JwtUtill;
import com.example.ArtNew.service.LoginService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    JwtUtill jwtUtill;

    @Autowired
    LoginService loginService;

    @Autowired
    AuthenticationManager authenticationManager;

    //59 min all code except controller

    @PostMapping("/login")
    public Object authenticateUser(@RequestBody JwtRequest jwtRequest){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword());
        try {
            authenticationManager.authenticate(authToken);
        }
        catch (BadCredentialsException e){
            e.printStackTrace();
            //throw new BadCredentialsException("invalid credentials");
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Invalid Crdetials!!");
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Invalid Crdetials!!");
        }

        UserDetails userDetails = loginService.loadUserByUsername(jwtRequest.getEmail());
        String token = jwtUtill.generateToken(userDetails);
        JwtResponse jwtResponse = new JwtResponse(token, 200);
        return ResponseEntity.ok(jwtResponse);
    }
}
