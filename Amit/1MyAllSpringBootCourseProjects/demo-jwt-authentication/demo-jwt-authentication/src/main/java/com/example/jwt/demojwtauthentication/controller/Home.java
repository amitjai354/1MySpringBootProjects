package com.example.jwt.demojwtauthentication.controller;

import com.example.jwt.demojwtauthentication.security.MyUser;
import com.example.jwt.demojwtauthentication.security.MyUserCreateRequest;
import com.example.jwt.demojwtauthentication.security.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Home {

    @Autowired
    MyUserService myUserService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('NORMAL')") //instead of config, assigning authority here.. in config make this authenticate
    @GetMapping("/normal/welcome")
    public String normalWelcome(){
        String text="this is private page for normal user ";
        text += "this page is not allowed to unauthenticated user";
        return text;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/welcome")
    public String adminWelcome(){

        //not passing user id but getting it from securuty context internally
        MyUser myUser1=(MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("id :"+ myUser1.getId());//printing on console
        System.out.println(myUser1);


        String text="this is private page for admin user ";
        text += "this page is not allowed to unauthenticated user\n" +"id :"+ myUser1.getId()+"\n"+myUser1;
        return text;
    }

    @GetMapping("/public/welcome")
    public String publicWelcome(){
        String text="this is public page ";
        text += "this page is  allowed to all user";
        return text;
    }

    @PostMapping("/myuser")
    public MyUser createwMyUser(@RequestBody MyUserCreateRequest myUserCreateRequest){
        //not passing user id but getting it from securuty context internally
//        MyUser myUser1=(MyUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        myUser1.getId();


        //we need to encrypt password as we have created bean Password encoder so if store raw password..
        //it will match encoded password with raw obne so bad credengtials
        //UserCreateRequest class is not Component class so can not autowire Password Encoder there..
        //Instead of building user objedct in that class.. build here
        MyUser myUser = MyUser.builder()
                .username(myUserCreateRequest.getUsername())
                .password(passwordEncoder.encode(myUserCreateRequest.getPassword()))
                .authority(myUserCreateRequest.getAuthority())
                .build();
        return myUserService.createUser(myUser);
    }
}
