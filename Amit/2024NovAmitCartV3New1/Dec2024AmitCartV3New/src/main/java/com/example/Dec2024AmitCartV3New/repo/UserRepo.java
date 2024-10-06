package com.example.Dec2024AmitCartV3New.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Dec2024AmitCartV3New.models.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String username);
}
