package com.example.demo.amitSbProject.repositories;

import com.example.demo.amitSbProject.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
