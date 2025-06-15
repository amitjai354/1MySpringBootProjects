package com.example.innovator24Dec.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.innovator24Dec.entity.Design;
import com.example.innovator24Dec.repository.DesignRepository;
import com.example.innovator24Dec.service.DesignService;

@RequestMapping("/design")
public class DesignController {

	private DesignService designService;
	
	private DesignRepository designRepository;
	
	@PostMapping("/add")
	public ResponseEntity<Object> postDevice(Design design){
		return null;
	}
	
	@GetMapping("/list")
	public ResponseEntity<Object> getDesign(){
		return null;
	}
	
	@GetMapping("/get/{designId}")
	public ResponseEntity<Object> getDesignDataWithId(int designId){
		return null;
	}
	
	@GetMapping("/filter")
	public ResponseEntity<Object> getFilter(int price){
		return null;
	}
	
	@DeleteMapping("/delete/{designId}")
	public ResponseEntity<Object> deleteDesign(int designId){
		return null;
	}
	
	@PutMapping("/update/{designId}")
	public ResponseEntity<Object> updateDesign(int designId, Design design){
		return null;
	}
}
