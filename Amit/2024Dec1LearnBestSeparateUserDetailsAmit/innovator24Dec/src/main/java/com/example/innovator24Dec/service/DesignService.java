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
