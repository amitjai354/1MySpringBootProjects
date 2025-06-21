package com.example.innovator24Dec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.innovator24Dec.entity.Design;
import com.example.innovator24Dec.entity.UserInfo;
import com.example.innovator24Dec.repository.DesignRepository;
import com.example.innovator24Dec.repository.UserInfoRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class DesignService {

	@Autowired
	private DesignRepository designRepository;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	//create
	//201 - created
	//400 - Bad Request
	public ResponseEntity<Object> postDesign(Design design){
		//Date myDate = new Date();
		//SimpleDateFormat dtformat = new SimpleDateFormat("dd/MM/yyyy");
		//dtformat.format(myDate);//return type of this is formatted date
		//but myDate will still be same
		//ticketModel.setCreatedAt(dtformat.format(myDate));
		
		
		/*
		//to get the current date this test case given in exam last time
		//here using Calender to get date but i am using Date to get cutrrent date
		public String gettime() {
			String x = String.valueOf(System.currentTimeMillis());
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");//we tell how we want to format date
			long milliseconds = Long.parseLong(x);
			Calendar calendar= Calendar.getInstance();
			calendar.setTimeInMillis(milliseconds);
			return formatter.format(calendar.getTime());
		}
		*/
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfo userInfo = userInfoRepository.findByName(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("username not found"));
			
			design.setDesignerId(userInfo.getId());
			Design designFromDb = designRepository.save(design);
			
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(designFromDb);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in post api");
		}
	}
	
	//List design data
	//200 ok
	//400 Bad request
	public ResponseEntity<Object> getDesign(){
		try {
			
			List<Design> designList = designRepository.findAll();
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(designList);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get list api");
		}
	}
	
	//List the design data using design id where availabilityStatus == "In Stock" and price<=2000
	//200 ok
	//400 condition does not match
	//400 bad request
	public ResponseEntity<Object> getDesignDataWithId(int designId){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfo userInfo = userInfoRepository.findByName(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("username not found"));
			
			Design designFromDb = designRepository.findById(designId).orElse(null);
			if(designFromDb == null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("No design data found with specific id");
			}
			
			if(designFromDb.getAvailabilityStatus().equals("In Stock") && designFromDb.getPrice()<=2000) {
				return ResponseEntity.status(HttpServletResponse.SC_OK).body(List.of(designFromDb));
			}
			else {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("The conditions specified do not match with the expected criteria.");
			}
			
//			List<Design> designList = designRepository.findByDesignIdAndAvailabilityStatusEqualsAndPriceLessThanEqual(designId, "In Stock", 2000);
//			if(designList.isEmpty()) {
//				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("The conditions specified do not match with the expected criteria.");
//			}
//			else {
//				return ResponseEntity.status(HttpServletResponse.SC_OK).body(designList);
//			}
			
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get design by id api");
		}
	}
	
	//List the design data using price as request param
	//200 ok
	//400 Bad Request
	public ResponseEntity<Object> getPrice(int price){
		try {
			
			List<Design> designList = designRepository.findByPrice(price);
			if(designList.isEmpty()) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in post api");
			}
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(designList);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get design by price api");
		}
	}
	
	//update design data using design id
	//200 ok
	//400 bad request
	//403 Forbidden
	public ResponseEntity<Object> updateDesign(int designId, Design design){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfo userInfo = userInfoRepository.findByName(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("username not found"));
			
			Design designFromDb = designRepository.findById(designId).orElse(null);
			if(designFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Requested data could not be found");
			}
			
			if(designFromDb.getDesignerId() != userInfo.getId()) {
				return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("you dont have permission");
			}
			
			designFromDb.setAvailabilityStatus(design.getAvailabilityStatus());
			designFromDb.setPrice(design.getPrice());
			
			designFromDb = designRepository.save(designFromDb);
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(designFromDb);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in update api");
		}
	}
	
	//delete design data using design id
	//204 No Content
	//400 bad request
	//403 Forbidden
	public ResponseEntity<Object> deleteDesign(int designId){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfo userInfo = userInfoRepository.findByName(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("username not found"));
			
			Design designFromDb = designRepository.findById(designId).orElse(null);
			if(designFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Requested data could not be found");
			}
			
			if(designFromDb.getDesignerId() != userInfo.getId()) {
				return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("you dont have permission");
			}
			
			designRepository.deleteById(designId);
			
			return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("deleted succesfully");
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in delete api");
		}
	}
}
