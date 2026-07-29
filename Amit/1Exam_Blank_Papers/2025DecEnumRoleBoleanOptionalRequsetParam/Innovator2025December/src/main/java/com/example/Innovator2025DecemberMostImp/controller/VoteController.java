package com.example.Innovator2025DecemberMostImp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Innovator2025DecemberMostImp.entity.Vote;
import com.example.Innovator2025DecemberMostImp.service.VoteService;

@RequestMapping("/vote")
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
	GET /vote/List/{id}
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
	
	---------------------------------------------------------
	
	3. GET VOTES
	GET /vote/List/{activityId}
	
	Responses:
	200 OK
	204 No Content
	
	--------------------------------------------------------
	
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
	
	//only 2 apis here in exam
	
	@PostMapping("/add/{activityId}")
	public ResponseEntity<Vote> postVote(int activityId, Vote vote){
		return null;
	}
	
	
	///here this was given in exam only : Optional<Boolean> support
	//in service i wrote boolean support but i should have written Optional<Boolean> support there as well
	@GetMapping("/list/{activityId}")
	public ResponseEntity<List<Vote>> getSupportersAndOpposers(@PathVariable int activityId, Optional<Boolean> support){
		//return VoteService.getSupportersAndOpposers(activityId, false);
		//here if required we do not need to pass any value for support
		return voteService.getSupportersAndOpposers(activityId, support); //this shpuld also work
		//when calling the api, even if does not pass support, then also it will work
		//support can be false, true or null
		//this will be handled while calling the api only
		//call api: /vote/list/2?support=flase
	}
	
	
	
	
}
