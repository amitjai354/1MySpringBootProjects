package com.example.Innovator2025June28.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Innovator2025June28.entity.Station;
import com.example.Innovator2025June28.entity.UserInfo;
import com.example.Innovator2025June28.repository.StationRepo;
import com.example.Innovator2025June28.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;


@Service
public class StationService {
	
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
	StationRepo stationRepo;
	
	@Autowired
	UserRepository userRepository;
	
	public ResponseEntity<Object> postStationData(Station station){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfo userInfo = userRepository.findByName(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("amit username not found"));
			
			station.setOperatorId(userInfo.getId());
			
			station = stationRepo.save(station);
			
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(station);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("amit error station service");
		}
	}
	
	public ResponseEntity<Object> getData(){
		try {
			List<Station> stationList = stationRepo.findAll();
			if(stationList.isEmpty()) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("data not found");
			}
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(stationList);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("amit error station service");
		}
	}
	
	public ResponseEntity<Object> updateData(int station_id, Station station){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfo userInfo = userRepository.findByName(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("amit username not found"));
			
			Station stationDb = stationRepo.findById(station_id).orElse(null);
			if(stationDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("no data found");
			}
			
			if(stationDb.getOperatorId() != userInfo.getId()) {
				return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("you do not have permission");
			}
			
			stationDb.setFrequency(station.getFrequency());
			stationDb.setCountry(station.getCountry());
			
			stationDb = stationRepo.save(stationDb);
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(stationDb);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("amit error station service");
		}
	}

}
