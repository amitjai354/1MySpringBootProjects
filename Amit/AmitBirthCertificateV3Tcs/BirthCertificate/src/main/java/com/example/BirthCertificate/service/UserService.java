package com.example.BirthCertificate.service;

import com.example.BirthCertificate.model.UserModel;
import com.example.BirthCertificate.repository.RoleRepository;
import com.example.BirthCertificate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UserModel getUserByEmail(String email){
        //return userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("username not found"));
        UserModel userModel = userRepository.findByEmail(email).orElse(null);
        if (userModel==null){
            throw new UsernameNotFoundException("username not found");
        }
        return userModel;
    }

}
