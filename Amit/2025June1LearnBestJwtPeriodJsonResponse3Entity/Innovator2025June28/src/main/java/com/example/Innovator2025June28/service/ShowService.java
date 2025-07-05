package com.example.Innovator2025June28.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.Innovator2025June28.entity.Show;
import com.example.Innovator2025June28.repository.ShowRepo;

import jakarta.servlet.http.HttpServletResponse;


@Service
public class ShowService {
	
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
	ShowRepo showRepo;


	//station Id is id of the station to which show is associated
	//already passed in the show request body so need to separately add
	public ResponseEntity<Object> postShowData(Show show){
		try {
			show = showRepo.save(show);
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(show);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("amit error show service");
		}
	}
	

	public ResponseEntity<Object> getShowData(){
		try {
			List<Show> showList = showRepo.findAll();
			if(showList.isEmpty()) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("no data found");
			}
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(showList);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("amit error show service");
		}
	}
	

	public ResponseEntity<Object> getShowsByTiming(String showTime){
		try {
			List<Show> showList = showRepo.findByShowTime(showTime);
			if(showList.isEmpty()) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("no data found");
			}
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(showList);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("amit error show service");
		}
	}
	
	
	//in test case returning show with 5 star rating
	public ResponseEntity<List<Show>> getPopularShow(){
		try {
			List<Show> showList = showRepo.findByPopularityRating(5);
			if(showList.isEmpty()) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(null);
			}
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(showList);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(null);
		}
	}
}
