package com.example.Innovator24June.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
	/*
//signUp
{
    "name":"clientOne",
    "email":"client1@gmail.com",
    "password": "client123$",
    "roles":"CLIENT"
}
///ticket/add
{
 "ticketId":176345,
 "issue":"I'm locked out of my account. It says my account is suspended",
 "category":"Account Suspension",
 "comments":"Please help me resolve this issue"
}
{
 "ticketId":176346,
 "issue":"My computer wont start up. It shows a blue screen error.",
 "category":"Hardware",
 "comments":"I have tried restarting multiple times, but the issue persist"
}
	 */
	public ResponseEntity<Object> postTicket(TicketModel ticketModel){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfo userInfo = userInfoRepository.findByName(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("invalid username"));
			ticketModel.setClientId(userInfo.getId());
			
			Date myDate = new Date();
			SimpleDateFormat dtformat = new SimpleDateFormat("dd/MM/yyyy");
			//dtformat.format(myDate);//return type of this is formatted date
			//but myDate will still be same
			
			ticketModel.setCreatedAt(dtformat.format(myDate));
			
			ticketModel = ticketRepository.save(ticketModel);
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(ticketModel);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in saving ticket");
		}
	}
	
	public ResponseEntity<Object> updateTicket(int id, TicketModel ticketModel){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfo userInfo = userInfoRepository.findByName(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("invalid username"));
			
			TicketModel ticketModelFromDb = ticketRepository.findById(id).orElse(null);
			if(ticketModelFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("not found");
			}
			ticketModelFromDb.setPriority(ticketModel.isPriority());
			ticketModelFromDb.setStatus(ticketModel.getStatus());
			ticketModelFromDb = ticketRepository.save(ticketModelFromDb);
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(ticketModelFromDb);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in updating ticket");
		}
	}
	
	//Ok 200
	//Bad request 400
	public ResponseEntity<Object> getTicket(String category){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfo userInfo = userInfoRepository.findByName(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("invalid username"));
			
			List<TicketModel> ticketList = ticketRepository.findByCategory(category);
			if(ticketList.isEmpty()) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("ticketList is empty");
			}
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(ticketList);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in getting ticket");
		}
	}
	
	public ResponseEntity<Object> deleteTicket(int id){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfo userInfo = userInfoRepository.findByName(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("invalid username"));
			
			TicketModel ticketModel = ticketRepository.findById(id).orElse(null);
			if(ticketModel==null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("not found");
			}
			if(ticketModel.getClientId()==userInfo.getId()) {
				ticketRepository.deleteById(id);
				return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("deleted successfully");
			}
			else {
				return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("you don't have permission");
			}
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in deleting ticket");
		}
	}
}
