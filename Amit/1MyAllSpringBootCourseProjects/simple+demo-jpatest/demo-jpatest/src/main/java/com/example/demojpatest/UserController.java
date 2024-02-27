package com.example.demojpatest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    UserCreateRequest userCreateRequest;

    @PostMapping("/createUser")
    public User createUser(@RequestBody UserCreateRequest userCreateRequest){
        return userCreateRequest.to();
    }
}
