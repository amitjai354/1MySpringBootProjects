package com.example.Dec2024AmitCartV3New.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Dec2024AmitCartV3New.DTO.JwtRequest;
import com.example.Dec2024AmitCartV3New.DTO.JwtResponse;
import com.example.Dec2024AmitCartV3New.config.JwtUtill;
import com.example.Dec2024AmitCartV3New.models.Product;
import com.example.Dec2024AmitCartV3New.models.User;
import com.example.Dec2024AmitCartV3New.repo.ProductRepo;
import com.example.Dec2024AmitCartV3New.repo.UserRepo;
import com.example.Dec2024AmitCartV3New.service.UserAuthService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/public")
public class PublicController {
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserAuthService userAuthService;
	
	@Autowired
	private JwtUtill jwtUtill;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	//Example: /api/public/product/search?keyword="tablet"
	//This endpoint takes a query parameter 'keyword' and 
	//returns all the matching products containing the keyword either in productName or categoryName.
	@GetMapping("/product/search")
	public List<Product> getProducts(@RequestParam String keyword) throws Exception{
		//Example: /api/public/product/search? keyword="tablet"
		/*
		 returns: [{"productId":1,"productName":"Apple iPad 10.2 8th Gen WiFi iOS Tablet",
		 "price":29190.0,
		 "category": {"categoryName":"Electronics"}},
 		 {"productId":2,
 		 "productName":"Crocin pain relief tablet",
 		 "price":10.0,
 		 "category":{"categoryName":"Medicines"}}]
		 */
		try {
			List<Product> productList = productRepo.findByProductNameContainingIgnoreCaseOrCategoryCategoryNameContainingIgnoreCase(keyword, keyword);  
			return productList;//if no such data present then, it will be empty list
			//here return type is not Response Entity so can not send any status like if productList.isEmpty
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new Exception("error in getting product public api "+e.getMessage());
		}
		
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
	
	
	//request body - {"username": "bob","password": "pass_word"}
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody JwtRequest jwtRequest){
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
				jwtRequest.getUsername(), jwtRequest.getPassword());
		try {
			authenticationManager.authenticate(authToken);
		}
		catch (BadCredentialsException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("amit error in login");
			//throw new BadCredentialsException("invalid username or password");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("amit error in login");
		}
		
		UserDetails userDetails = userAuthService.loadUserByUsername(jwtRequest.getUsername());
		String token = jwtUtill.generateToken(userDetails.getUsername());
		JwtResponse jwtResponse = new JwtResponse(token, 200);
		return ResponseEntity.status(HttpServletResponse.SC_OK).body(token);
	}
}
