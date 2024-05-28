package com.example.demo.amitSbProject.repositories;

import com.example.demo.amitSbProject.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<MyUser, Integer> {

    MyUser findUserByUsername(String username);




}
