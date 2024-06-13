package com.example.ArtNew.service;

import com.example.ArtNew.model.UserModel;
import com.example.ArtNew.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserService {

    @Autowired
    UserRepo userRepo;

    UserModel getByEmail(String email){
        return userRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("Username invalid"));
//        UserModel userModel = userRepo.findByEmail(email).orElse(null);
//        if(userModel==null){
//            throw new UsernameNotFoundException("Invalid username");
//        }
//        return userModel;
    }
}
