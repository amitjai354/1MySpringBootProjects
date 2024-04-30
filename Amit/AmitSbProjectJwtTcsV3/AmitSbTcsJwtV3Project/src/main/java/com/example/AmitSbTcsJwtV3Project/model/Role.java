package com.example.AmitSbTcsJwtV3Project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.security.core.GrantedAuthority;

public enum Role {
    CONSUMER,
    SELLER;
}

//@Entity
//public class Role{
//    @Id
//    private Integer roleId;
//    private String role;
//    //we have 2 roles only consumer and seller, so no need to autoincrement roleid
//    //how we will store role if not enum.. need to store in db then
//
//
//    public Role() {
//    }
//
//    public Role(Integer roleId, String role) {
//        this.roleId = roleId;
//        this.role = role;
//    }
//
//    public Integer getRoleId() {
//        return roleId;
//    }
//
//    public void setRoleId(Integer roleId) {
//        this.roleId = roleId;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//}


class RoleGrantedAuthority implements GrantedAuthority{

    String role;

    public RoleGrantedAuthority(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return this.role;
    }
}
