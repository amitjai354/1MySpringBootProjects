package com.example.Innovator2025DecemberMostImp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.Innovator2025DecemberMostImp.entity.Vote;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class VoteService {
	
	public ResponseEntity<Vote> postVote(int activityId, Vote vote){
		return null;
	}
	
//	public ResponseEntity<List<Vote>> getSupportersAndOpposers(int activityId, boolean support){
//		try {
//			return ResponseEntity.status(HttpServletResponse.SC_OK).body(null);
//		}
//		catch (Exception e) {
//			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(null);
//		}
//	}
	
	
	///here this was given in exam only : Optional<Boolean> support
	//in service i wrote boolean support but i should have written Optional<Boolean> support there as well
	public ResponseEntity<List<Vote>> getSupportersAndOpposers(int activityId, Optional<Boolean> support){
		try {
			//create one method in the vote repository which takes optional support in the input
			//means if passing false, or true or no value then api shpuold handle all this
			//or we can manually check here in the code if support = null then call find all
			//else call find all by false or true
			
			//find on chatgpt by pasting service and controller method
			//Push to git this
			
			//create method for this
			//findByVoteStatusAndActivityActivityId
			//ican do something like this if support == null then use other method ekse use other method
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(null);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(null);
		}
	}

}
