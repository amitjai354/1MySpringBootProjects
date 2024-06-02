package com.example.AmitCartV3NewTcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.AmitCartV3NewTcs.model.Category;
import com.example.AmitCartV3NewTcs.model.Product;
import com.example.AmitCartV3NewTcs.model.UserModel;
import com.example.AmitCartV3NewTcs.repository.CategoryRepo;
import com.example.AmitCartV3NewTcs.repository.ProductRepo;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class SellerService {
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CategoryRepo categoryRepo;
	
	public ResponseEntity<Object> findAllProductOwnedBySeller(){
		UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			//if we will do find all, it will return products added by other sellers also
			//but we need products added by logged in seller only so findBySellerUserId
			List<Product> productListFromDb= productRepo.findBySellerUserId(userModel.getUserId());
			if(productListFromDb.isEmpty()) {
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("No Product present for logged in seller");
			}
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(productListFromDb);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
		}
	}
	
	public ResponseEntity<Object> findProductByProductIdAndOwnedBySeller(int productId){
		UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			//if we will do find all, it will return products added by other sellers also
			//but we need products added by logged in seller only so findBySellerUserId
			Product productFromDb= productRepo.findByProductIdAndSellerUserId(productId, userModel.getUserId());
			if(productFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("No Product present for given product id and logged in seller");
			}
			return ResponseEntity.status(HttpServletResponse.SC_OK).body(productFromDb);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
		}
	}
	
	public ResponseEntity<Object> saveProductBySellerId(Product product){
		UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			Product productFromDb=productRepo.findByProductIdAndSellerUserId(product.getProductId(), userModel.getUserId());
			if(productFromDb!=null) {
				return ResponseEntity.status(HttpServletResponse.SC_CONFLICT).body("product already added by seller");
			}
			Category categoryFromDb = categoryRepo.findByCategoryName(product.getCategory().getCategoryName());
			if(categoryFromDb == null) {
				categoryFromDb = categoryRepo.save(product.getCategory());
			}
			product.setCategory(categoryFromDb);
			product.setSeller(userModel);//create relationship between product and seller
			productFromDb = productRepo.save(product);
			
			String url = "localhost:8080/seller/product/" + productFromDb.getProductId();
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(url);
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
		}
	}
	
	public ResponseEntity<Object> updateProductBySellerId(Product product){
		UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
//			Product productFromDb=productRepo.findByProductIdAndSellerUserId(product.getProductId(), userModel.getUserId());
//			if(productFromDb!=null) {
//				productFromDb=productRepo.save(product);
//				return ResponseEntity.status(HttpServletResponse.SC_OK).body(productFromDb);
//			}
//			else {
				Category categoryFromDb = categoryRepo.findByCategoryName(product.getCategory().getCategoryName());
				if(categoryFromDb == null) {
					categoryFromDb = categoryRepo.save(product.getCategory());
				}
				product.setCategory(categoryFromDb);
				product.setSeller(userModel);//create relationship between product and seller
				Product productFromDb = productRepo.save(product);
				return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(productFromDb);
//			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();
		}
	}
	
	public ResponseEntity<Object> deleteProductBySellerId(int  productId){
		try {
			UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Product productFromDb= productRepo.findByProductIdAndSellerUserId(productId, userModel.getUserId());
			if(productFromDb==null) {
				return ResponseEntity.status(HttpServletResponse.SC_NOT_FOUND).body("Product not owned by seller so can not delete");
			}
			productRepo.deleteById(productId);
			//we have already put check that no user other than seller can access this in security config
			//also just above we put the check that if product does not belong to seller then return 404
			//in get we have to find by user id, but since we already checked findBuy user id so
			//now no need to check delete by user id
			return ResponseEntity.status(HttpServletResponse.SC_NO_CONTENT).body("Succesfully deleted");
			//in 204, this is not error, this is startting from 2 and error msg start from 4
			//so succesfully deleted so no content.. 
			//in 204 does not returns body, it discards body
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().build();
		}
		
	}

}
