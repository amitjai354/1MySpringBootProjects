package com.example.AmitCartV3NewTcs.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.AmitCartV3NewTcs.model.UserModel;

public interface UserRepo extends JpaRepository<UserModel, Integer>{
	Optional<UserModel> findByEmail(String email);
}
