package com.example.Dec2024AmitCartV3New.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Dec2024AmitCartV3New.models.Category;
import com.example.Dec2024AmitCartV3New.models.Product;
import com.example.Dec2024AmitCartV3New.models.User;
import com.example.Dec2024AmitCartV3New.repo.CartProductRepo;
import com.example.Dec2024AmitCartV3New.repo.CartRepo;
import com.example.Dec2024AmitCartV3New.repo.CategoryRepo;
import com.example.Dec2024AmitCartV3New.repo.ProductRepo;
import com.example.Dec2024AmitCartV3New.repo.UserRepo;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth/seller")
public class SellerController {
	
	@Autowired
	CartRepo cartRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CartProductRepo cartProductRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@PostMapping("/product")
	public ResponseEntity<Object> postProduct(@RequestBody Product product){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userRepo.findByUsername(userDetails.getUsername()).orElse(null);
			
			Product productFromDb=productRepo.findBySellerUserIdAndProductId(user.getUserId(), product.getProductId()).orElse(null);
			if(productFromDb!=null) {
				return ResponseEntity.status(HttpServletResponse.SC_CONFLICT).body("product already added by seller");
			}
			
			Category categoryFromDb = categoryRepo.findByCategoryName(product.getCategory().getCategoryName()).orElse(null);
			if(categoryFromDb==null) {
				categoryFromDb = categoryRepo.save(new Category(product.getCategory().getCategoryName()));
			}
			
			product.setCategory(categoryFromDb);
			product.setSeller(user);
			
			productFromDb = productRepo.save(product);
			
			String url = "localhost:8080/seller/product/" + product.getProductId();
			
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(url);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get customer cart");
		}
	}

	
	//200 return all the products owned by seller.
	@GetMapping("/product")
	public ResponseEntity<Object> getAllProducts(){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User myUser = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("username not found"));
			List<Product> productList = productRepo.findBySellerUserId(myUser.getUserId());
			
			if(productList.isEmpty()) {
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("no product found");
			}
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(productList);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get customer cart");
		}
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<Object> getProduct(@PathVariable int productId){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User myUser = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("username not found"));
			
			Product productFromDb = productRepo.findBySellerUserIdAndProductId(myUser.getUserId(), productId).orElse(null);
			if(productFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("no product found");
			}
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(productFromDb);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get customer cart");
		}
	}

	@PutMapping("/product")
	public ResponseEntity<Object> putProduct(@RequestBody Product product){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User myUser = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("username not found"));

			Product productFromDb = productRepo.findBySellerUserIdAndProductId(myUser.getUserId(), product.getProductId())
					.orElse(null);
			
			if(productFromDb == null) {
				//save product first by saving category..
				//if not null then also save but we do not need to set catgeory now again.
				//productFromDb=productRepo.save(product);
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("Product Not Found");
				//this wa requirement in art that while updating or deleting if not found then return not found
			}
			
			if(productFromDb.getSeller().getUserId() != myUser.getUserId()) {
				return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("You don't have permission!!");
			}
			
			Category categoryFromDb = categoryRepo.findByCategoryName(product.getCategory().getCategoryName()).orElse(null);
			if(categoryFromDb==null) {
				categoryFromDb = categoryRepo.save(new Category(product.getCategory().getCategoryName()));
			}
			
			product.setCategory(categoryFromDb);
			product = productRepo.save(product);
			
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(product);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get customer cart");
		}
	}
	
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<Object> deleteProduct(@PathVariable int productId){
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User myUser = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("username not found"));

			Product productFromDb = productRepo.findBySellerUserIdAndProductId(myUser.getUserId(), productId).orElse(null);
			if(productFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("product not found");
			}
			
			if(productFromDb.getSeller().getUserId() != myUser.getUserId()) {
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("product not found");
			}
			
			productRepo.deleteById(productId);
			
			return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("deleted successfully");
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in get customer cart");
		}
	}
}
