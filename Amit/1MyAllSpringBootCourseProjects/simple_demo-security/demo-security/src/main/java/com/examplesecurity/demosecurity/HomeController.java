package com.examplesecurity.demosecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @Autowired
    MyUserService myUserService;

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
        String text="this is private page for admin user ";
        text += "this page is not allowed to unauthenticated user";
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
        return myUserService.createUser(myUserCreateRequest.to());
    }
}
