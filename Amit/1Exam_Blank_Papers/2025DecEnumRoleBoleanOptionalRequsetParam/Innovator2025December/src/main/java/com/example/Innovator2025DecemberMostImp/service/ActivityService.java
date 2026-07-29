package com.example.Innovator2025DecemberMostImp.service;

import org.springframework.http.ResponseEntity;

import com.example.Innovator2025DecemberMostImp.dto.UpdateActivityDto;

public class ActivityService {

	public ResponseEntity<Object> getActivity(){
		return null;
	}
	

	public ResponseEntity<Object> addActivity(){
		return null;
	}
	

	public ResponseEntity<Object> updateActivity(int id, UpdateActivityDto activityDto){
		//vry important here in update they have created new Activity DTO
		//here they have attribute status
		//status is ENUM so need to check if this should be written ot not:
		//@Enumerated(EnumType.ORDINAL)
		//My code was giving forbidden as in update any Organizer can update no constraint they had given there
		//i did not check dto there if anything was there
		return null;
	}
	

	public ResponseEntity<Object> deleteActivity(int id){
		return null;
	}
	
}
