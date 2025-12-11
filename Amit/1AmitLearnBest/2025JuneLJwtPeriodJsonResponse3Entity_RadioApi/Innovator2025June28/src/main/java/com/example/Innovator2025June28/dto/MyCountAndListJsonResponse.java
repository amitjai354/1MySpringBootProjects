package com.example.Innovator2025June28.dto;

import java.util.List;

import com.example.Innovator2025June28.entity.Station;

public class MyCountAndListJsonResponse {
	
	//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, // Starts server on defined port (e.g., 8080)
    //            properties = {"spring.h2.console.enabled=true"}) // Overrides property to enable console, this we already write in properties file so no need

	int count;
	List<Station> stationList;
	
	public MyCountAndListJsonResponse() {
		super();
	}

	public MyCountAndListJsonResponse(int count, List<Station> stationList) {
		super();
		this.count = count;
		this.stationList = stationList;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<Station> getStationList() {
		return stationList;
	}

	public void setStationList(List<Station> stationList) {
		this.stationList = stationList;
	}
	
	
}
