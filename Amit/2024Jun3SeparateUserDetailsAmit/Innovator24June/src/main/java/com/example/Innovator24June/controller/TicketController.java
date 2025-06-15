package com.example.Innovator24June.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Innovator24June.entity.TicketModel;
import com.example.Innovator24June.service.TicketService;



@RequestMapping("/ticket")
@RestController
public class TicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@PostMapping("/add")
	public ResponseEntity<Object> postTicket(@RequestBody TicketModel ticketModel){
		return ticketService.postTicket(ticketModel);
	}
	
	@GetMapping("/list")
	public ResponseEntity<Object> getTicket(@RequestParam("category") String category){
		return ticketService.getTicket(category);
	}

	@PatchMapping("/update/{id}")
	public ResponseEntity<Object> updateTicket(@PathVariable int id, @RequestBody TicketModel ticketModel){
		return ticketService.updateTicket(id, ticketModel);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Object> deleteTicket(@PathVariable("id") int id){
		return ticketService.deleteTicket(id);
	}
}
