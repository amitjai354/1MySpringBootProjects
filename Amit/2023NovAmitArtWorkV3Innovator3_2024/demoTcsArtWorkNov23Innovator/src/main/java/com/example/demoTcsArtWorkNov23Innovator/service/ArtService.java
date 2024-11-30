package com.example.demoTcsArtWorkNov23Innovator.service;

import com.example.demoTcsArtWorkNov23Innovator.model.ArtModel;
import com.example.demoTcsArtWorkNov23Innovator.model.UserModel;
import com.example.demoTcsArtWorkNov23Innovator.repository.ArtRepository;
import com.example.demoTcsArtWorkNov23Innovator.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtService {

	@Autowired
    ArtRepository artRepository;

  	@Autowired
    UserService userService;
  	
  	@Autowired
  	UserRepository userRepository;

    //created 201 and bad request 400
    public ResponseEntity<Object> postArtWork(ArtModel artModel){
    	try {
    		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		UserModel userModel = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("invalild username"));
    		
    		artModel.setOwnerId(userModel.getId());
    		artModel = artRepository.save(artModel);
    		return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(artModel);
    	}
    	catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in posting artwork");
		}
    }

    //200 and 400
    public ResponseEntity<Object> getArtWork(String medium){
    	try {
    		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		UserModel userModel = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("invalild username"));
    		
    		List<ArtModel> artList = artRepository.findByMedium(medium);
    		if(artList.isEmpty()) {
    			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("no data available");
    		}
    		return ResponseEntity.status(HttpServletResponse.SC_OK).body(artList);
    	}
    	catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in getting artwork");
		}
    }

    //to update art work price and available status
    //only the owner of that particular artwork can update
    //200, 400, 403 forbidden
    public ResponseEntity<Object> updateArtWork(int id, ArtModel artModel){
    	try {
    		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		UserModel userModel = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("invalild username"));
    		
    		ArtModel artModelFromDb = artRepository.findById(id).orElse(null);
    		if(artModelFromDb==null) {
    			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("not found");
    		}
    		
    		if(userModel.getId()!=artModelFromDb.getOwnerId()) {
    			return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("you don't have permission");
    		}
    		
    		artModelFromDb.setPrice(artModel.getPrice());
    		artModelFromDb.setAvailable(artModel.isAvailable());
    		artModelFromDb = artRepository.save(artModelFromDb);
    		
    		return ResponseEntity.status(HttpServletResponse.SC_OK).body(artModelFromDb);
    	}
    	catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in updating artwork");
		}
    }

    //to delete artwork by using id
    //only the owner of that particular artwork can update
    //no content 204 and bad request 400 and forbidden 403
    public ResponseEntity<Object> deleteArtWork(int id){
    	try {
    		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    		UserModel userModel = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()-> new UsernameNotFoundException("invalild username"));
    		
    		ArtModel artModelFromDb = artRepository.findById(id).orElse(null);
    		if(artModelFromDb==null) {
    			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("not found");
    		}
    		
    		if(userModel.getId()!=artModelFromDb.getOwnerId()) {
    			return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("you don't have permission");
    		}
    		
    		artRepository.deleteById(id);
    		
    		return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("deleted successfully");
    	}
    	catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in posting artwork");
		}
    }

}
