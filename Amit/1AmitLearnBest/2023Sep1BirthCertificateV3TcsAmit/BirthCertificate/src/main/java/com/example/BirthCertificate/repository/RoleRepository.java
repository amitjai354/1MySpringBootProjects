package com.example.BirthCertificate.repository;

import com.example.BirthCertificate.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleModel, Integer> {
}
