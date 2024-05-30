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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
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
            //throw new BadCredentialsException("invalid credentials");
            //@RestControllerAdvise then @ExceptionHandler to print msg when this Bad credential exception
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Invalid credentials");
            //this is must to return or throw error otherwise on wrong credentials still working
            //Message this is printing but status is not 400 it is 200 ok
            //so response entity is good.. this is working with 400
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Invalid credentials");
        }
        UserDetails userDetails = loginService.loadUserByUsername(authenticationRequest.getEmail());
        String token = jwtUtill.generateToken(userDetails);

        JwtResponse jwtResponse = new JwtResponse(token, 200);
        //return ResponseEntity.status(HttpServletResponse.SC_OK).body(jwtResponse);
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }
}
