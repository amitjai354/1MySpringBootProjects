package com.example.Dec2024AmitCartV3New.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Dec2024AmitCartV3New.DTO.JwtRequest;
import com.example.Dec2024AmitCartV3New.DTO.JwtResponse;
import com.example.Dec2024AmitCartV3New.config.JwtUtill;
import com.example.Dec2024AmitCartV3New.models.Product;
import com.example.Dec2024AmitCartV3New.service.UserAuthService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/public")
public class PublicController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserAuthService userAuthService;
	
	@Autowired
	private JwtUtill jwtUtill;

	@GetMapping("/product/search")
	public List<Product> getProducts(){
		return null;
	}
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody JwtRequest jwtRequest){
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				jwtRequest.getUsername(), jwtRequest.getPassword());
		try {
			authenticationManager.authenticate(authToken);
		}
		catch(Exception e) {
			e.printStackTrace();
			//return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("invalid username or password");
			throw new BadCredentialsException("invalid username or password");
		}
		UserDetails userDetails = userAuthService.loadUserByUsername(jwtRequest.getUsername());
		String token = jwtUtill.generateToken(userDetails.getUsername());
		JwtResponse jwtResponse = new JwtResponse(token, 200);
		return ResponseEntity.status(HttpServletResponse.SC_OK).body(token);
	}
}
