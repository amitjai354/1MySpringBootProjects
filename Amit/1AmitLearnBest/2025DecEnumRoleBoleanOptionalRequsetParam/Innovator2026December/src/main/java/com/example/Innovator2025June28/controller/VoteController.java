package com.example.Innovator2025June28.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.Innovator2025June28.entity.Vote;
import com.example.Innovator2025June28.service.VoteService;


@RestController
public class VoteController {
	
	@Autowired
	VoteService voteService;

	
	/*
	  
	========================
	6. ACCESS RULES
	========================
	
	Public → No auth
	Organizer → ORGANIZER role
	Citizen → CITIZEN role
	Owner → Organizer + creator
	
	Failure → 403
	
	========================
	7. ENDPOINTS
	========================
	
	POST /Login
	GET /activity/List
	GET /vote/List/{id}
	POST /activity/add
	PATCH /activity/update/{id}
	DELETE /activity/delete/{id}
	POST /vote/add/{id}
	
	IMPORTANT:
	Case-sensitive endpoints
	
	========================
	
	
	
	
	=======================================
	7. API ENDPOINTS
	=========================================
	
	1. LOGIN
	POST /Login
	
	Request:
	{
	  "username": "organizerOne",
	  "password": "organizer123$"
	}
	
	Responses:
	200 OK
	400 Bad Request
	
	---------------------
	
	2. GET ACTIVITIES
	GET /activity/List
	
	Responses:
	200 OK
	204 No Content
	
	---------------------
	
	3. GET VOTES
	GET /vote/List/{activityId}
	
	Responses:
	200 OK
	204 No Content
	
	---------------------
	
	4. ADD ACTIVITY (Organizer only)
	POST /activity/add
	
	Responses:
	201 Created
	401 Unauthorized
	403 Forbidden
	
	---------------------
	
	5. UPDATE ACTIVITY
	PATCH /activity/update/{id}
	
	Request:
	{
	  "status": "CLOSED"
	}
	
	Responses:
	200 OK
	400 Bad Request
	403 Forbidden
	
	---------------------
	
	6. DELETE ACTIVITY
	DELETE /activity/delete/{id}
	
	Responses:
	204 Deleted
	400 Not Found
	403 Forbidden
	
	---------------------
	
	7. ADD VOTE (Citizen only)
	POST /vote/add/{activityId}
	
	Request:
	{
	  "support": false,
	  "comment": "I oppose"
	}
	
	Responses:
	201 Created
	401 Unauthorized
	403 Forbidden
	
	===================================================
	
	 */
	
	
	@GetMapping("/list/{activityId}")
	public ResponseEntity<List<Vote>> getAll(@PathVariable int activityId, Optional<Boolean> support){
		//return VoteService.getSupportersAndOpposers(activityId, false);
		//here if required we do not need to pass any value for support
		return voteService.getSupportersAndOpposers(activityId, support); //this shpuld also work
		//when calling the api, even if does not pass support, then also it will work
		//support can be false, true or null
		//this will be handled while calling the api only
	}
	
	
}
