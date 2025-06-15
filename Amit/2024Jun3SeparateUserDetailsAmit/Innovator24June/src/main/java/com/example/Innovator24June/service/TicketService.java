package com.example.Innovator24June.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


import com.example.Innovator24June.entity.TicketModel;
import com.example.Innovator24June.entity.UserInfo;
import com.example.Innovator24June.repository.TicketRepository;
import com.example.Innovator24June.repository.UserInfoRepository;

import jakarta.servlet.http.HttpServletResponse;


@Service
public class TicketService {

	@Autowired
	private TicketRepository ticketRepository;
	
	@Autowired
	private UserInfoRepository userInfoRepository;
	
	//created 201
	//badRequest 400
	public ResponseEntity<Object> postTicket(TicketModel ticketModel){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfo userInfo = userInfoRepository.findByName(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("invalid username password"));
			
			ticketModel.setClientId(userInfo.getId());
			
			Date dt = new Date();
			SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yyyy");
			ticketModel.setCreatedAt(dtFormat.format(dt));
			//@CreationTimestamp given in Entity class for time stamp.. comes from hibernate library.. spring data jpa
			
			ticketModel = ticketRepository.save(ticketModel);
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(ticketModel);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in saving ticket");
		}
	}
	
	//Ok 200
	//Bad request 400
	public ResponseEntity<Object> getTicket(String category){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfo userInfo = userInfoRepository.findByName(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("invalid username password"));
			
			List<TicketModel> listTicket = ticketRepository.findByCategory(category);
			if(listTicket.isEmpty()) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("no data available");
			}
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(listTicket);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in getting ticket");
		}
	}
	
	public ResponseEntity<Object> updateTicket(@PathVariable int id, @RequestBody TicketModel ticketModel){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfo userInfo = userInfoRepository.findByName(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("invalid username password"));
			
			TicketModel ticketModelFromDb = ticketRepository.findById(id).orElse(null);
//			if(ticketModelFromDb==null) {
//				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("not found");
//			}
			
			ticketModelFromDb.setPriority(ticketModel.isPriority());
			ticketModelFromDb.setStatus(ticketModel.getStatus());
			
			ticketModelFromDb = ticketRepository.save(ticketModelFromDb);
			//i was saving ticketModel instead of ticketModelFromDb so test case was failing that assertion failed
			//priority and status were not matching..
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(ticketModelFromDb);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in updating ticket");
		}
	}
	
	public ResponseEntity<Object> deleteTicket(int id){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfo userInfo = userInfoRepository.findByName(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("invalid username password"));
			
			TicketModel ticketModelFromDb = ticketRepository.findById(id).orElse(null);
			if(ticketModelFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("not found");
			}
			
			if(ticketModelFromDb.getClientId() != userInfo.getId()) {
				return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("you don't have permission");
			}
			
			ticketRepository.deleteById(id);
			
			return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("deleted successfully");
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in deleting ticket");
		}
	}
}
