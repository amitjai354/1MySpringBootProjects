package com.example.AmitSbTcsJwtV3Project.controller;

import com.example.AmitSbTcsJwtV3Project.config.JwtUtil;
import com.example.AmitSbTcsJwtV3Project.model.JwtRequest;
import com.example.AmitSbTcsJwtV3Project.model.Product;
import com.example.AmitSbTcsJwtV3Project.repository.ProductRepo;
import com.example.AmitSbTcsJwtV3Project.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PublicController {

    @Autowired
    UserAuthService myUserDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ProductRepo productRepo;

    @GetMapping("/product/search")
    public List<Product> getProducts(@RequestParam("keyword") String keyword){
        return productRepo.findByProductNameIgnoreCaseContainingOrCategoryCategoryNameIgnoreCaseContaining(keyword.toUpperCase(), keyword.toUpperCase());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody JwtRequest jwtRequest){
        //since passing username password to verfify user and generate tokenin jwt request so use UsenamePaswordAuthenticatiionToken
        // and pass username , password in it
        //then authticate useing authenticationManager bean that we created

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),jwtRequest.getPassword());
        try{
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }
        catch (BadCredentialsException e){
            e.printStackTrace();//only prints on screen but does not stop program by throwing any exception
            throw new BadCredentialsException("Username or Password is incorrect");
            //this error show in console only not in api so need ExceptionHandler
        }
        //once authenticated just return token..
        //setting authentication is done in onceperrequest filter
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        //return ResponseEntity.ok(token);
        //return ResponseEntity.ok().body(token);
        //return  new ResponseEntity<>(token, HttpStatus.OK);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
