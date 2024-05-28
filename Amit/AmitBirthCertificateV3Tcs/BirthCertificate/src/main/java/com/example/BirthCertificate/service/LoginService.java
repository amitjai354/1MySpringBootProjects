package com.example.BirthCertificate.service;

import com.example.BirthCertificate.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class LoginService implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return null;
    }

    private UserDetails buildUserForAuthentication(UserModel user, List<GrantedAuthority> list1){
        return null;
    }

    private List<GrantedAuthority> buildUserAuthority(String userRole){
        return null;
    }
}
