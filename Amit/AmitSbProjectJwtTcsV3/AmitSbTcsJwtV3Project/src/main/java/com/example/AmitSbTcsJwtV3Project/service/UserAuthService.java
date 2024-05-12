package com.example.AmitSbTcsJwtV3Project.service;

import com.example.AmitSbTcsJwtV3Project.model.User;
import com.example.AmitSbTcsJwtV3Project.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User myUser = userRepo.findByUsername(username);
        if (myUser==null){
            throw new UsernameNotFoundException("Username not found");
        }
        return userRepo.findByUsername(username);
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
        //when returning username not found we are getting 500 bad request and Bad credential exception
    }
}
