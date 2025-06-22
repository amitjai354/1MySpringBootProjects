package com.example.Dec2024AmitCartV3New.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Dec2024AmitCartV3New.config.JwtUtill;
import com.example.Dec2024AmitCartV3New.dto.JwtRequest;
import com.example.Dec2024AmitCartV3New.dto.JwtResponse;
import com.example.Dec2024AmitCartV3New.models.Product;
import com.example.Dec2024AmitCartV3New.models.User;
import com.example.Dec2024AmitCartV3New.repo.UserRepo;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/public")
public class PublicController {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtill jwtUtill;
	
	@Autowired
	UserRepo userRepo;
	

	@GetMapping("/product/search")
	public List<Product> getProducts(){
		return null;
	}
	
	
	@PostMapping("/signUp")
	public ResponseEntity<User> addUser(@RequestBody User user){
		try {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user = userRepo.save(user);
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(user);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new User());
		}	
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody JwtRequest jwtRequest){
		UsernamePasswordAuthenticationToken authToken = new 
				UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword());
		try {
			authenticationManager.authenticate(authToken);
		}
		catch (BadCredentialsException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("amit error in login");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("amit error in login");
		}
		
		String token = jwtUtill.generateToken(jwtRequest.getUsername());
		JwtResponse jwtResponse = new JwtResponse(token, 200);
		return ResponseEntity.status(HttpServletResponse.SC_OK).body(jwtResponse);
	}
}
