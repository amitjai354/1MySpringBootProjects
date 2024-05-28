package com.example.BirthCertificate.repository;

import com.example.BirthCertificate.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
}
