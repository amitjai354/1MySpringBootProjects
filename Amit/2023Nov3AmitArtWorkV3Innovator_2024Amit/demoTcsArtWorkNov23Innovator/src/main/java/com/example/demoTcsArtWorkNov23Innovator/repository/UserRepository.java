package com.example.demoTcsArtWorkNov23Innovator.repository;

import com.example.demoTcsArtWorkNov23Innovator.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;


public interface UserRepository extends JpaRepository<UserModel, Integer> {
    //add required annotation to make the art repository
	
	Optional<UserModel> findByEmail(String email);
	Optional<UserModel> findByUsername(String username);
}
