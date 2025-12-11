package com.example.demoTcsArtWorkNov23Innovator.repository;

import com.example.demoTcsArtWorkNov23Innovator.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleModel, Integer> {
    //add required annotation to make the art repository
}
