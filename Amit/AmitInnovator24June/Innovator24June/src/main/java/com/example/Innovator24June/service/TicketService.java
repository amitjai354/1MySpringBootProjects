package com.example.Innovator24June.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.Innovator24June.config.UserInfoUserDetails;
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
	private UserInfoRepository userInfoRepository; //this was given in exam.. so need to use now
	
	//created 201
	//badRequest 400
	public ResponseEntity<Object> postTicket(TicketModel ticketModel){
		//UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//earlier UserModel used to implement userDetails so this was working..
		//UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserInfoUserDetails userInfoUserDetails = (UserInfoUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//in exam autowired userInfooRepository.. for this purpose
		UserInfo userInfo = userInfoRepository.findByName(userInfoUserDetails.getUsername()).orElseThrow(()-> new BadCredentialsException("invalid username"));
		
		//ticketModel.setCreatedAt(new Date().toString());//new Date(System.currentTimeMillis())
		//Date is in DD/MM/YYYY format
		Date dt = new Date();
		SimpleDateFormat dtFormatter = new SimpleDateFormat("dd/MM/yyyy");
		ticketModel.setCreatedAt(dtFormatter.format(dt));
		
		String username = userInfoUserDetails.getUsername();
		ticketModel.setClientId(userInfo.getId());
		try{
			ticketModel=ticketRepository.save(ticketModel);
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(ticketModel);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Could not add ticket");
		}
	}
	
	//Ok 200
	//Bad request 400
	public ResponseEntity<Object> getTicket(String category){
		try {
			List<TicketModel> ticketList = ticketRepository.findByCategory(category);
			if(ticketList.isEmpty()) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("no ticket for this category");
			}
			//if remove this, will give 200 even if no content. but as per failed test case.. it should give 400
			//if if write SC_NOT_FOUND, then gives 404 instead of 400
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(ticketList);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Could not fetch ticket for category");
		}
	}
	
	public ResponseEntity<Object> updateTicket(int id, TicketModel ticketModel){
		try {
			//TicketModel ticketModelFromDb = ticketRepository.findById(id).orElseThrow(()-> new BadCredentialsException("No ticket present with given id"));
			//TicketModel ticketModelFromDb = ticketRepository.findById(id).get();
			//TicketModel ticketModelFromDb = ticketRepository.findById(id).orElse(new TicketModel());
			TicketModel ticketModelFromDb = ticketRepository.findById(id).orElse(null);
			//if(ticketModelFromDb.getId()==0)
			if(ticketModelFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("no ticket with given id");
			}
			ticketModelFromDb.setPriority(ticketModel.isPriority());
			ticketModelFromDb.setStatus(ticketModel.getStatus());
			ticketModelFromDb = ticketRepository.save(ticketModelFromDb);
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(ticketModelFromDb);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Could not update ticket");
		}
	}
	
	public ResponseEntity<Object> deleteTicket(int id){
		try {
			//UserInfo userInfo = (UserInfo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			//earlier UserModel used to implement userDetails so this was working..
			//UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserInfoUserDetails userInfoUserDetails = (UserInfoUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			//in exam autowired userInfooRepository.. for this purpose
			UserInfo userInfo = userInfoRepository.findByName(userInfoUserDetails.getUsername()).orElseThrow(()-> new BadCredentialsException("invalid username"));
			
			TicketModel ticketModel = ticketRepository.findById(id).orElse(null);
			if(ticketModel == null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("not found");
			}
			
			if(userInfo.getId()!=id) {
				return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("you don't have permission");
			}
			//this should be below as per test case.. first not found then forbidden
			
			ticketRepository.deleteById(id);
			return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("successfully deleted");
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("could not delete");
		}
	}
}
