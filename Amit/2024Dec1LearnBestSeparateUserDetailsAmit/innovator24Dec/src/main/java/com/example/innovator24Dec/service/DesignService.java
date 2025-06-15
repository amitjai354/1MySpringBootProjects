package com.example.innovator24Dec.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.innovator24Dec.entity.Design;
import com.example.innovator24Dec.entity.UserInfo;
import com.example.innovator24Dec.repository.DesignRepository;
import com.example.innovator24Dec.repository.UserInfoRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service //as autowired in DesignController class
public class DesignService {

	@Autowired
	private DesignRepository designRepository;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	
	//create
	//201 - created : DESIGNER only can access
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
			UserInfo userInfo = userInfoRepository.findByName(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("usename not found"));
			//userDetails have name, password, authorities but it does not have userId so need userInfo
			
			design.setDesignerId(userInfo.getId());
			Design designFromdb = designRepository.save(design);
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(designFromdb);
			//again had given 200 instead of 201
			
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in post design api");
		}
	}
	
	
	//List design data
	//200 ok : public api can access without authentication
	//400 Bad request
	public ResponseEntity<Object> getDesign(){
		//private String imageURL; in Design class
		//i mistakenly wrote imageUrl here but in test case it was imageURL.. so causing error Caused by: 
		//com.jayway.jsonpath.PathNotFoundException: No results for path: $[0]['imageURL']
		//not finding the path $[0]['imageURL'] as in Design class it was imageUrl and json path is taking names from Design class only
		//even after Refactor here, getter and setter names were not changed
		//when i refactored them manually then it started working as json path internally uses getter and setter
		try {
			List<Design> designListFromDb = designRepository.findAll();
			if(designListFromDb.isEmpty()) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("design list is empty");
			}
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(designListFromDb);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get design list api");
		}
	}
	
	
	//List the design data using design id where availabilityStatus == "In Stock" and price<=2000
	//this is not list api as we are finding by id.. List word is confusing here
	//200 ok : DESIGNER OR USER both can access
	//400 condition does not match: The conditions specified do not match with the expected above criteria
	//400 bad request : No design data is found with the specific id
	public ResponseEntity<Object> getDesignDataWithId(int designId){
		//Caused by: com.jayway.jsonpath.PathNotFoundException: Filter: [0]['designId'] can only be applied 
		//to arrays. Current context is: {designId=1, designName=Stylish Backpack, description=Durable and 
		//stylish backpack for everyday use., color=Olive Green, price=990, availabilityStatus=In Stock, 
		//imageURL=http://example.com/images/backpack.jpg, availableSize=One Size, designerId=1}
		
		//here $[0].designId in test case means expecting list but i am returning single object so this error
		//return ResponseEntity.status(HttpServletResponse.SC_OK).body(List.of(designFromDb));
		
		try {
						
			Design designFromDb = designRepository.findById(designId).orElse(null);
			if(designFromDb == null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("No design data is found with the specific id");
			}
			
			
//			if(designFromDb.getAvailabilityStatus().equals("In Stock") && designFromDb.getPrice() <=2000 ) {
//				//return ResponseEntity.status(HttpServletResponse.SC_OK).body(designFromDb);
//				return ResponseEntity.status(HttpServletResponse.SC_OK).body(List.of(designFromDb));
//			}
//			else {
//				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("The conditions specified do not match with the expected above criteria");
//			}
			
			Design designFromDb1 = designRepository.findByDesignIdAndAvailabilityStatusEqualsAndPriceIsLessThanEqual(designId, "In Stock", 2000).orElse(null);
			if(designFromDb1 == null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("The conditions specified do not match with the expected above criteria");
				//they check only status not the messag ein test cases
			}
			else {
				return ResponseEntity.status(HttpServletResponse.SC_OK).body(List.of(designFromDb));
			}
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get design with id api");
		}
	}
	
	
	//List the design data using price as request param
	//200 ok : public api can access without authentication
	//400 Bad Request : No designs found with specific price
	public ResponseEntity<Object> getPrice(int price){
		//java.lang.AssertionError: Status expected:<400> but was:<405>
		//here this is get but in test case i had written put so getting this error
		try {
			List<Design> designListFromDb = designRepository.findByPrice(price);
			if(designListFromDb.isEmpty()) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("No designs found with specific price");
			}
			return  ResponseEntity.status(HttpServletResponse.SC_OK).body(designListFromDb);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get design by price filter api");
		}
	}
	
	
	//update design data using design id : availabilityStatus, price
	//200 ok : DESIGNER only can access
	//400 bad request : Requested data could not be found //they check only status not the messag ein test cases
	//403 Forbidden : you don't have permission
	public ResponseEntity<Object> updateDesign(int designId, Design design){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfo userInfo = userInfoRepository.findByName(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("usename not found"));
			//userDetails have name, password, authorities but it does not have userId so need userInfo
			
			Design designFromDb = designRepository.findById(designId).orElse(null);
			if(designFromDb == null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Requested data could not be found");
			}
			if(designFromDb.getDesignerId() != userInfo.getId()) {
				return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("you don't have permission");
			}
			
			designFromDb.setAvailabilityStatus(design.getAvailabilityStatus());
			designFromDb.setPrice(design.getPrice());
			designFromDb = designRepository.save(designFromDb);
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(designFromDb);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in update design api");
		}
	}
	
	
	//delete design data using design id
	//204 No Content  : DESIGNER only can access : deleted successfully
	//400 bad request : if given id is not found
	//403 Forbidden : you don't have permission
	public ResponseEntity<Object> deleteDesign(int designId){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfo userInfo = userInfoRepository.findByName(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("usename not found"));
			//userDetails have name, password, authorities but it does not have userId so need userInfo
			
			Design designFromDb = designRepository.findById(designId).orElse(null);
			if(designFromDb == null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Requested data could not be found");
			}
			if(designFromDb.getDesignerId() != userInfo.getId()) {
				return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("you don't have permission");
			}
			
			designRepository.deleteById(designId);
			return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("deleted successfully");
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in delete design api");
		}
	}
}
