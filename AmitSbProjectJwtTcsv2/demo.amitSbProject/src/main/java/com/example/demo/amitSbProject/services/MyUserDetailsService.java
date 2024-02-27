package com.example.demo.amitSbProject.services;

import com.example.demo.amitSbProject.models.MyUser;
import com.example.demo.amitSbProject.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findUserByUsername(username);
//        Optional<MyUser> myUser= Optional.ofNullable(userRepo.findUserByUsername(username));
//        if(myUser.isPresent()){
//            return myUser.get();
//        }
//        else{
//            throw new UsernameNotFoundException("User id not found");
//        }

    }

//    public MyUser loadUserByUserId(Integer id){
//        Optional<MyUser> myUser = userRepo.findById(id);
//        if(myUser.isPresent()){
//            return myUser.get();
//        }
//        else{
//            throw new UsernameNotFoundException("User id not found");
//        }
//    }


}
