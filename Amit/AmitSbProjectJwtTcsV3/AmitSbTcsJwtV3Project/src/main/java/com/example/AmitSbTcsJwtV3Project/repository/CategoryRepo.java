package com.example.AmitSbTcsJwtV3Project.repository;

import com.example.AmitSbTcsJwtV3Project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
    public Optional<Category> findByCategoryName(String categoryName);
}
