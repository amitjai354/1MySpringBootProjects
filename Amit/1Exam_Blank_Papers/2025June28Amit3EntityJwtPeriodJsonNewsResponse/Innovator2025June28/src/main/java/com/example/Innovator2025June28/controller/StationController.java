package com.example.Innovator2025June28.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.Innovator2025June28.entity.Station;

public class StationController {
	
	
	//Error: JWT signature does not match locally computed signature. JWT validity cannot be asserted and should not be trusted
	@PostMapping("/station/add")
	public ResponseEntity<Object> postData(Station station){
		return null;
	}
	
	@GetMapping("/station/list")
	public ResponseEntity<Object> getData(){
		return null;
	}
	
	@PutMapping("/station/update/{station_id}")
	public ResponseEntity<Object> updateData(int station_id, Station station){
		return null;
	}

}
