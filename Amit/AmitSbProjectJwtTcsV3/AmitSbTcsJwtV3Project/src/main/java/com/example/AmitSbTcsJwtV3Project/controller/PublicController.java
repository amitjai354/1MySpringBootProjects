package com.example.AmitSbTcsJwtV3Project.controller;

import com.example.AmitSbTcsJwtV3Project.config.JwtUtil;
import com.example.AmitSbTcsJwtV3Project.model.JwtRequest;
import com.example.AmitSbTcsJwtV3Project.model.Product;
import com.example.AmitSbTcsJwtV3Project.repository.ProductRepo;
import com.example.AmitSbTcsJwtV3Project.service.UserAuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
		
        //when password is incorrect then Bad credentials
        //but if username is wrong then UserNameNotFoundException
        //so global error handlinhg: invalid credentials working only when password is wrong
        //but if username wrong then userDetailsService returns null, this is UsernameNotFound
        //so better at end also give Exception e
        //Spring Security hides the username does not exist exception by wrapping it so that when
        // 4developing, developers only get BadCredentialsException
        catch (BadCredentialsException e){
            e.printStackTrace();//only prints on screen but does not stop program by throwing any exception
            throw new BadCredentialsException("Username or Password is incorrect");
            //this error show in console only not in api so need ExceptionHandler
        }
        catch (UsernameNotFoundException e){
            e.printStackTrace();
            throw new UsernameNotFoundException("Username is wrong");
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
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
