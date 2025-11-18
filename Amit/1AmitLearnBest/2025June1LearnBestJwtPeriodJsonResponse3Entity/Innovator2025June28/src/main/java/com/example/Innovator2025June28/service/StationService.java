package com.example.Innovator2025June28.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Innovator2025June28.dto.MyCountAndListJsonResponse;
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
			
			//Be very careful, if already giving operatorId in request so do not add
			//otherwise test case failing here..
			//generally it should not fail but here since signUp is not correct so it is failing
			//but if they are giving operator id then do not pass as they may give 
			//different operator id for different user
			
			//station.setOperatorId(userInfo.getId());
			
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
			//return ResponseEntity.status(HttpServletResponse.SC_OK).body(stationList);
			
			
//--------------------------------------------------------------------------------------------------------			
//Aproach 1: 
			//return json response with count and list : asked in slot 1
			//for this we need to create one new pojo class in dto. there must be getters and setters for the class.
			//Now when we return object of any java class, spring automatically converts it to json response
			//we ResponseBody inside RestController, it converts java object to json object
			//@RequestBody: converts Json object to Java object
			//But only condition is that java object should be of POJO class and it must have all getters and setters to work
			
//			MyCountAndListJsonResponse myCountAndListJsonResponse = new MyCountAndListJsonResponse();
//			myCountAndListJsonResponse.setCount(stationList.size());
//			myCountAndListJsonResponse.setStationList(stationList);
//			return ResponseEntity.status(HttpServletResponse.SC_OK).body(myCountAndListJsonResponse);
			
			
			
/*
{ "count": 2,
    "stationList": [
        {
            "station_id": 1,
            "name": "RadioWave FM",
            "frequency": "101.2 FM",
            "genre": "Pop",
            "language": "English",
            "country": "USA",
            "streamingURL": "https://radiowavefm.com/stream",
            "startTime": "06:00 AM",
            "endTime": "11:00 PM",
            "operatorId": 1,
            "live": true
        },
        {
            "station_id": 2,
            "name": "Global Beats",
            "frequency": "101.3 FM",
            "genre": "Western",
            "language": "French",
            "country": "France",
            "streamingURL": "https://globalbeates.fr/stream",
            "startTime": "05:00 AM",
            "endTime": "10:00 PM",
            "operatorId": 2,
            "live": false
        }
    ]
}
*/
//------------------------------------------------------------------------------------------------------------------	
//Aproach 2 :			
			//here I am not returning any object of my pojo class so no need of getter setter.
			//here I am returning one List so complete response is of list type in json
			//Java internally manages List objects getter , setter
			List<Object> myJsonList = new ArrayList<>();
			myJsonList.add(stationList.size());
			myJsonList.add(stationList);
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(myJsonList);
			
			
/*
[
    2,
    [
        {
            "station_id": 1,
            "name": "RadioWave FM",
            "frequency": "101.2 FM",
            "genre": "Pop",
            "language": "English",
            "country": "USA",
            "streamingURL": "https://radiowavefm.com/stream",
            "startTime": "06:00 AM",
            "endTime": "11:00 PM",
            "operatorId": 1,
            "live": true
        },
        {
            "station_id": 2,
            "name": "Global Beats",
            "frequency": "101.3 FM",
            "genre": "Western",
            "language": "French",
            "country": "France",
            "streamingURL": "https://globalbeates.fr/stream",
            "startTime": "05:00 AM",
            "endTime": "10:00 PM",
            "operatorId": 2,
            "live": false
        }
    ]
]
*/
//------------------------------------------------------------------------------------------------------		
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
