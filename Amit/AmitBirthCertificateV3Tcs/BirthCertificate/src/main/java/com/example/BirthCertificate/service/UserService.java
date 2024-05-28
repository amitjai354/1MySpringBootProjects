package com.example.BirthCertificate.service;

import com.example.BirthCertificate.model.UserModel;
import com.example.BirthCertificate.repository.RoleRepository;
import com.example.BirthCertificate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    public UserModel getUserByEmail(String email){
        return null;
    }

}
