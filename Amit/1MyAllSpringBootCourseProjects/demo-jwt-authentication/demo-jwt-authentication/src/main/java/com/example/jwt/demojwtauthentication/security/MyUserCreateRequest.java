package com.example.jwt.demojwtauthentication.security;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyUserCreateRequest {

    private String username;

    private String password;
    private String authority;

    //as can not autowire PasswordEncoder here so builduing object in controllwer class
//    public MyUser to(){
//        return MyUser.builder()
//                .username(this.username)
//                .password(this.password)
//                .authority(this.authority)
//                .build();
//    }
}
