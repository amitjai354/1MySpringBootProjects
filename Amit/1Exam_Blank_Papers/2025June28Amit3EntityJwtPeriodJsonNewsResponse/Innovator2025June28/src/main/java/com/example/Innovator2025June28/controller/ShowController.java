package com.example.Innovator2025June28.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.Innovator2025June28.entity.Show;
import com.example.Innovator2025June28.entity.Station;

public class ShowController {

	
	@PostMapping("/show/add")
	public ResponseEntity<Object> postData(Show show){
		return null;
	}
	
	@GetMapping("/show/list")
	public ResponseEntity<Object> getData(){
		return null;
	}
	
	@GetMapping("/show/get/airing")
	public ResponseEntity<Object> getShowByTiming(String showTime){
		return null;
	}
	
	@GetMapping("/show/popularShow")
	public ResponseEntity<List<Show>> getPopularShow(){
		return null;
	}
}
