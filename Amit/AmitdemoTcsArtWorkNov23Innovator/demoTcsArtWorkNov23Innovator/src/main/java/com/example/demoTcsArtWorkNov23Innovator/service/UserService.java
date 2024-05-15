package com.example.demoTcsArtWorkNov23Innovator.service;

import com.example.demoTcsArtWorkNov23Innovator.model.UserModel;
import com.example.demoTcsArtWorkNov23Innovator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    //public class UserService implements UserDetailsService {
    //this is done in login service

    @Autowired
    UserRepository userRepository;

    //given in exam
    public UserModel getUserByEmail(String email){
        //return userRepository.findByEmail(email).orElseThrow(()->new RuntimeException());
        return userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("Username not found"));
        //if returning runtime exception then getting user details ervice null exception
        //when returning username not found exception after making it optional, getting Bad credentials excptio
        //now everyehere can directly use this method, no need to check null, as we user user service not user repo
        //at any plave we need this method
    }

    //Spring Security hides the username does not exist exception by wrapping it so that when developing,
    //developers only get BadCredentialsException
    //when we throw username not found exception then Spring wraps it and returns Bad credential
    //as if we say username not found , hacker will know surely that he need to change username only
    //when returning username not found, we are getting 500 bad request Bad credential exception
    //as Spring internally wrapy username not fopiund to bad credentials exception

    //we need user details service to for Dao Authentication.. username and password saved in Db
    //provider.setUserDetailsService(); in DaoAuthentication Bean
    //Below method returns userdetails so we need user details hence user model implements user details
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username);
//    }
    public UserModel getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("Username not found!!"));
    }
}
