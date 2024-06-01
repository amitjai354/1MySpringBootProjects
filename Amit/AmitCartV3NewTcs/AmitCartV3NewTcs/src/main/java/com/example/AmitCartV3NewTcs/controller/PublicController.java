package com.example.AmitCartV3NewTcs.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.AmitCartV3NewTcs.DTO.JwtRequest;
import com.example.AmitCartV3NewTcs.DTO.JwtResponse;
import com.example.AmitCartV3NewTcs.config.JwtUtill;
import com.example.AmitCartV3NewTcs.service.MyUserDetailsService;
import com.example.AmitCartV3NewTcs.service.ProductService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
//@RequestMapping("/public")
public class PublicController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	MyUserDetailsService myUserDetailsService;
	
	@Autowired
	JwtUtill jwtUtill;
	
	@Autowired
	ProductService productService;
	
	@GetMapping("/product/search")
	public ResponseEntity<Object> getProductByKeyword(@RequestParam("keyword") String keyword){
		return productService.findProductByKeyword(keyword, keyword);
	}

	@PostMapping("/login")
	public Object authenticateUser (@RequestBody JwtRequest jwtRequest) {
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword());
		try {
			authenticationManager.authenticate(authToken);
		}
		catch (BadCredentialsException e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new BadCredentialsException("username or password is wrong");
			//return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Invalid Credentials!!");
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("Invalid Credentials!!");
		}
		
		UserDetails userDetails = myUserDetailsService.loadUserByUsername(jwtRequest.getEmail());
		String token = jwtUtill.generateToken(userDetails);
		JwtResponse jwtResponse = new JwtResponse(token, 200);
		return ResponseEntity.status(HttpServletResponse.SC_OK).body(jwtResponse);
	}
}
