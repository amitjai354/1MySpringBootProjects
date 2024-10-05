package com.example.AmitSbTcsJwtV3Project.repository;

import com.example.AmitSbTcsJwtV3Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
    //since already passed User above in class name so no need of findByUserUsername
    //if any class other User then : findByUsernameProductProductname.. something like this
}
