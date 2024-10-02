package com.example.Innovator24June.service;

import org.springframework.http.ResponseEntity;

import com.example.Innovator24June.entity.TicketModel;
import com.example.Innovator24June.repository.TicketRepository;
import com.example.Innovator24June.repository.UserInfoRepository;



public class TicketService {

	private TicketRepository ticketRepository;
	
	private UserInfoRepository userInfoRepository;
	
	//created 201
	//badRequest 400
	public ResponseEntity<Object> postTicket(TicketModel ticketModel){
		return null;
	}
	
	//Ok 200
	//Bad request 400
	public ResponseEntity<Object> getTicket(String category){
		return null;
	}
	
	public ResponseEntity<Object> deleteTicket(int id){
		return null;
	}
}
