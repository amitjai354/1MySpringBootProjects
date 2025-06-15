package com.example.Dec2024AmitCartV3New.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Dec2024AmitCartV3New.models.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {
	Optional<Category> findByCategoryName(String category);
}
