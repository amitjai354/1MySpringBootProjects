package com.example.innovator24Dec.service;

import org.springframework.http.ResponseEntity;

import com.example.innovator24Dec.entity.Design;
import com.example.innovator24Dec.repository.DesignRepository;
import com.example.innovator24Dec.repository.UserInfoRepository;

public class DesignService {

	private DesignRepository designRepository;
	
	private UserInfoRepository userInfoRepository;
	
	//create
	//201 - created
	//400 - Bad Request
	public ResponseEntity<Object> postDesign(Design design){
		return null;
	}
	
	//List design data
	//200 ok
	//400 Bad request
	public ResponseEntity<Object> getDesign(){
		return null;
	}
	
	//List the design data using design id where availabilityStatus == "In Stock" and price<=2000
	//200 ok
	//400 condition does not match
	//400 bad request
	public ResponseEntity<Object> getDesignDataWithId(int designId){
		return null;
	}
	
	//List the design data using price as request param
	//200 ok
	//400 Bad Request
	public ResponseEntity<Object> getPrice(int price){
		return null;
	}
	
	//update design data using design id
	//200 ok
	//400 bad request
	//403 Forbidden
	public ResponseEntity<Object> updateDesign(int designId, Design design){
		return null;
	}
	
	//delete design data using design id
	//204 No Content
	//400 bad request
	//403 Forbidden
	public ResponseEntity<Object> deleteDesign(int designId){
		return null;
	}
}
