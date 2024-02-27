package com.example.demo.amitSbProject.models;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@ToString
//public class Role {
//    //insert for this table is not written in tcs data.sql means taken as enum.. but db is initialised
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int roleId;
//    private String role;
//}

public enum Role{
    CONSUMER,
    SELLER

}

@AllArgsConstructor
class RoleGrantedAuthority implements GrantedAuthority{
    String role;

    @Override
    public String getAuthority() {
        return this.role;
    }
}