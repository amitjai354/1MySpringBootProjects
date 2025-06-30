package com.example.Innovator2025June28.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Innovator2025June28.entity.Show;
import com.example.Innovator2025June28.entity.Station;
import com.example.Innovator2025June28.service.ShowService;


@RestController
public class ShowController {

//in security config in exam , I have written these
//Red api by "ADMIN", Green by "USER", Blue by anyone without requiring authentication, JWT Token
//Blue api: /signUp, /login, /station/list, /show/list
//Green: /show/get/airing, /show/popularShow
//Red: /station/add, /station/update, show/add

//Status code: check in test case for each api
// /signUp : 201 and nothing given in exam
// /login : 200, 400 Invalid Crdentials!, 
// /station/list : 200, in test case unauthrized and Forbidden but that handled from security config
// /show/list : 200 and nothing given in exam, in test case unauthrized and Forbidden but that handled from security config
// /show/get/airing : 200 400 if no data, 
// /show/popularShow : 200 and nothing given, in test case unauthrized and Forbidden but that handled from security config
// /station/add : those who have role ADMIN and operator id point to the owner who created station details 201
// /station/update : authenticated user have role ADMIN and is creator of id object, 200, Forbidden if not the creator, 400 if no data found
// /show/add : 201 400 is any validation issue like missing required fields or invalid station id

	@Autowired
	ShowService service;
	
	@PostMapping("/show/add")
	public ResponseEntity<Object> postData(@RequestBody Show show){
		return service.postShowData(show);
	}
	
	@GetMapping("/show/list")
	public ResponseEntity<Object> getData(){
		return service.getShowData();
	}
	
	
	// http://localhost:8081//show/get/airing/08:00 AM - 10:00 AM
	@GetMapping("/show/get/airing")
	public ResponseEntity<Object> getShowByTiming(@RequestParam String showTime){
		return service.getShowsByTiming(showTime);
	}
	
	@GetMapping("/show/popularShow")
	public ResponseEntity<List<Show>> getPopularShow(){
		return service.getPopularShow();
	}
}
