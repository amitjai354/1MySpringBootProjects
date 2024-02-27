package com.examplejwt.demojwtauthentication.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("amit")){
            return new User("amit", "amit123", new ArrayList<>());
            //Arrylist is for authority ist
            //we are not calling user from db.. we need to create MyUser Entity class for that
            //we are directly creating one user using User class
        }
        else {
            throw new UsernameNotFoundException("user not found");
        }
    }
}
