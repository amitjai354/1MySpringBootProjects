package com.example.demoTcsArtWorkNov23Innovator.service;

import com.example.demoTcsArtWorkNov23Innovator.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    UserService userService;

    //org.springframework.security.authentication.InternalAuthenticationServiceException:
    // UserDetailsService returned null, which is an interface contract violation
    //this exception is thrown if username is wrong while trying to generate token
    //if password wrong then BadCredentials exception

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        UserModel userModel = userService.getUserByEmail(email);
//        if (userModel==null){
//            throw new UsernameNotFoundException("Username not found");
//        }
        //no need of this code as we made this method optional in repo and returning usernamenotfound exceptionj in
        //user service only
        return userService.getUserByEmail(email);
        //Spring Security hides the username does not exist exception by wrapping it so that when developing,
        //developers only get BadCredentialsException
        //when we throw username not found exception then Spring wraps it and returns Bad credential
        //as if we say username not found , hacker will know surely that he need to change username only
        //if (userModel==null){
        //  throw new UsernameNotFoundException("Username not found");
        //}
        //when returning username not found we are getting 500 bad request Bad credential exception
    }

    //below methods are private then how will we call them in other classes, we need getter setter
    //so can not use in other classes.. can use in other methods in same class
    //if passing username in loadeUser by username there we can use this this method to get User details
    //then get emails then call find By username
    private UserDetails buildUserForAuthentication(UserModel userModel){
        //can not use above method in filter for authentication as there Token returns username from token
        //Claims::getSubject returns username not emails.. so can not use above method.. loadUserbyUsername(email)
        //use this one method in filter for authentication
        //we can generate usermodel from jwt util and then use this method to get user details
        return userModel;
    }

//    //my method
//    public UserDetails getUserDetailsForFilterAuthentication(){
//        return this.buildUserForAuthentication(u)
//    }

    private List<GrantedAuthority> buildUserAuthority(String u){
        return null;
    }
}
