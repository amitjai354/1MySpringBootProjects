package com.example.Innovator24June.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Innovator24June.entity.TicketModel;
import com.example.Innovator24June.service.TicketService;



@RequestMapping("/ticket")
@RestController
public class TicketController {
	
	private TicketService ticketService;
	
	@PostMapping("/add")
	public ResponseEntity<Object> postTicket(TicketModel ticketModel){
		return null;
	}
	
	@GetMapping("/list")
	public ResponseEntity<Object> getTicket(String category){
		return null;
	}

	@PatchMapping("/update/{id}")
	public ResponseEntity<Object> updateTicket(int id, TicketModel ticketModel){
		return null;
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteTicket(int id){
		return null;
	}
}
