package com.example.demoTcsArtWorkNov23Innovator.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "Users")//by default name is user_model
public class UserModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "Username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email", unique = true)
    private String email;

    private RoleModel role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Here return any collection, ? means collection can be anything list set vector
        //but it should extend GrantedAuthority
        //return Collections.singletonList(grantedAuthority);
        //used in security filter chain to get authority
        //we are returning singleton list because ManyToOne means each user has only one role
        //but if manyToMany then this will be list of roles
        //return role.stream.map(r-> new SimpleGrantedAuthority(r.getName()).collect(Collectors.toList());
    	
    	return null;
    }

    //constructor

    public UserModel() {
    }

    public UserModel(int id, String username, String password, String email, RoleModel role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public UserModel(String username, String password, String email, RoleModel role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public UserModel(String password, String email) {
        this.password = password;
        this.email = email;
    }

    //getter and setter
    
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RoleModel getRole() {
        return role;
    }

    public void setRole(RoleModel role) {
        this.role = role;
    }

    //to string
    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }
}
