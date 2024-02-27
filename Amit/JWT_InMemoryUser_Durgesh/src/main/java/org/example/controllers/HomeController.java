package org.example.controllers;

import org.example.model.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    UserService userService;
    @GetMapping("/users")
    public List<User> getUser(){
        System.out.println("Getting Users");
        return userService.getUserList();
    }

    @GetMapping("/current-user")
    public String getCurrentUser(Principal principal){
        return principal.getName();
    }

}
