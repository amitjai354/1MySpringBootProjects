package com.example.BirthCertificate.service;

import com.example.BirthCertificate.model.UserModel;
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
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userService.getUserByEmail(email);
    }

    private UserDetails buildUserForAuthentication(UserModel user, List<GrantedAuthority> list1){
        return null;
        //since our UserModel class is implementing UserDetails so we do not need this
        //if not implementing then here we need to convert user model and aithoprity to user details
    }

    private List<GrantedAuthority> buildUserAuthority(String userRole){
        return null;
    }
}
