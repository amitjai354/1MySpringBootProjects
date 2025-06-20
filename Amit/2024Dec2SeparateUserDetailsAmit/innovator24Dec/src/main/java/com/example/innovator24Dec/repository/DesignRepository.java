package com.example.innovator24Dec.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.innovator24Dec.entity.Design;

@Repository
public interface DesignRepository extends JpaRepository<Design, Integer> {

}
