package com.example.demojpatest;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateRequest {
    private String username;
    private String password;
    private String authority;

    public User to(){
        return User.builder().username(this.username)
                .password(this.password)
                .authority(this.authority)
                .build();
    }
}
