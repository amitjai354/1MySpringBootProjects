package com.example.Innovator2025June28.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Innovator2025June28.entity.Show;
import java.util.List;


public interface ShowRepo extends JpaRepository<Show, Integer>{

	List<Show> findByShowTime(String showTime);
	
	List<Show> findByPopularityRating(int popularityRating);
}
