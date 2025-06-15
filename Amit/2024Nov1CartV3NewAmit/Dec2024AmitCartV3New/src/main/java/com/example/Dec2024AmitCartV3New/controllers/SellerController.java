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
import com.example.Dec2024AmitCartV3New.repo.CategoryRepo;
import com.example.Dec2024AmitCartV3New.repo.ProductRepo;
import com.example.Dec2024AmitCartV3New.repo.UserRepo;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth/seller")
public class SellerController {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CartProductRepo cartProductRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	
	//status 201
	//redirect url (created URI)-	http://localhost/api/auth/seller/product/3
	@PostMapping("/product")
	public ResponseEntity<Object> postProduct(@RequestBody Product product){
		//takes product json in input and saves it to db..
		//save under category 'Electronics' so first this category should be in db 
		//then take category from db and update here in product object
		//Request body -
		//{"productId":3,"category":
		//{"categoryName":"Electronics","categoryId":"2"},"price":"98000.0","productName":"iPhone 12 Pro Max"}
		
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication();
			User user = userRepo.findByUsername(userDetails.getUsername()).orElse(null);//user will never be null
			//as in security filter chain we will handle everything..
			
			
			//this will save same product again and again with different product id.. something should be unique
			//like product name..
			Product productFromDb=productRepo.findBySellerUserIdAndProductId(user.getUserId(), product.getProductId()).orElse(null);
			if(productFromDb!=null) {
				return ResponseEntity.status(HttpServletResponse.SC_CONFLICT).body("product already added by seller");
			}
			//but issue is how will you get product id..
			//currently user id is being passed in the product json but this code is not needed in post api
			//as product id is generated after saving the product not before
			
			
			//Category categoryFromDb = categoryRepo.findByCategoryName(product.getCategory().getCategoryName())
			//		.orElse(categoryRepo.save(new Category(product.getCategory().getCategoryName())));
			//if no such category in db then save in db first
			Category categoryFromDb = categoryRepo.findByCategoryName(product.getCategory().getCategoryName())
					.orElse(null);
			if(categoryFromDb==null) {
				categoryFromDb = categoryRepo.save(new Category(product.getCategory().getCategoryName()));
			}
			product.setCategory(categoryFromDb); //otherwise gives some transient error..
			
			product.setSeller(user);
			
			product = productRepo.save(product);
			
			String url = "localhost:8080/seller/product/" + product.getProductId();
			
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(url);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("could not save seller product");
		}
	}

	//status 200
	@GetMapping("/product")
	public ResponseEntity<Object> getAllProducts(){
		//return all product owned by seller, not other sellers
		//in cart repo can not do find by id as will fetch all details other than product
		//product has category id and seller id
		//inside product repo..  findByUserSellerId
		//why working without category id
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication();
			User myUser = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("username not found"));
			List<Product> productList = productRepo.findBySellerUserId(myUser.getUserId());
			//can create method findBysellerUsername
			if(productList.isEmpty()) {
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("no product found");
			}
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(productList);
		}
		catch(Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in grtting all product");
		}
	}
	
	//status 200
	//Example: /api/auth/seller/product/1
	@GetMapping("/product/{productId}")
	public ResponseEntity<Object> getProduct(@PathVariable("productId") int productId){
		//in product repo, findById.. can not do this as will fetch other sellers product as well
		//product has category id and seller id
		//inside product repo..  findbyProductIdAndUserSellerId
		//findbyProductIdAndUserSellerIdAndCategoryCategoryId  why not this.. we have json ignore at category id
		//but that is at java input and response level, in table we have category id
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication();
			User user = userRepo.findByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("invalid username"));
			Product product = productRepo.findBySellerUserIdAndProductId(user.getUserId(), productId).orElse(null);
			if(product==null) {
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("no product found");
			}
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(product);
		}
		catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in getting product by id");
		}
	}

	//status 200
	@PutMapping("/product")
	public ResponseEntity<Object> putProduct(@RequestBody Product product){
		//takes a product json in request body with mandatory product id and updates the product in the database.
		//Request body -
		//{"productId":3,"category":
		//{"categoryName":"Electronics","categoryId":"2"},"price":"98000.0","productName":"iPhone 12 Pro Max"}
		
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication();
			User user = userRepo.findByUsername(userDetails.getUsername()).orElse(null);
			
			Product productFromDb = productRepo.findBySellerUserIdAndProductId(user.getUserId(), product.getProductId())
					.orElse(null);
			
			if(productFromDb == null) {
				//save product first by saving category..
				//if not null then also save but we do not need to set catgeory now again.
				//productFromDb=productRepo.save(product);
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("Product Not Found");
				//this wa requirement in art that while updating or deleting if not found then return not found
			}
			
			if (product.getSeller().getUserId() != user.getUserId()){
				return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("You don't have permission!!");
			}
			
			//Category categoryFromDb = categoryRepo.findByCategoryName(product.getCategory().getCategoryName())
			//		.orElse(categoryRepo.save(new Category(product.getCategory().getCategoryName())));
			//if no such category in db then save in db first
			Category categoryFromDb = categoryRepo.findByCategoryName(product.getCategory().getCategoryName())
					.orElse(null);
			if(categoryFromDb==null) {
				categoryFromDb = categoryRepo.save(new Category(product.getCategory().getCategoryName()));
			}

			product.setCategory(categoryFromDb); //otherwise gives some transient error..
			product = productRepo.save(product);
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(product);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("could not save seller product");
		}
	}
	
	//status 200 and If product not owned by seller return 404
	//Example: /api/auth/seller/product/1
	@DeleteMapping("/product/{productId}")
	public ResponseEntity<Object> deleteProduct(@PathVariable("productId") int productId){
		//takes a productId path parameter and 
		//deletes the product from the database. If product not owned by seller return 404
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication();
			User user = userRepo.findByUsername(userDetails.getUsername()).orElse(null);
			Product product = productRepo.findBySellerUserIdAndProductId(user.getUserId(), productId).orElse(null);
			if(product == null) {
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("product not found");
			}
			productRepo.deleteById(productId);
			return ResponseEntity.status(HttpServletResponse.SC_OK).body("deleted successfully");
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in deleting product");
		}
	}
}
