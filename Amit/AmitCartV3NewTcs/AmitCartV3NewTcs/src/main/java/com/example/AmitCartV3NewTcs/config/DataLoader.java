package com.example.AmitCartV3NewTcs.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.AmitCartV3NewTcs.model.Cart;
import com.example.AmitCartV3NewTcs.model.CartProduct;
import com.example.AmitCartV3NewTcs.model.Category;
import com.example.AmitCartV3NewTcs.model.Product;
import com.example.AmitCartV3NewTcs.model.RoleModel;
import com.example.AmitCartV3NewTcs.model.UserModel;
import com.example.AmitCartV3NewTcs.repository.CartProductRepo;
import com.example.AmitCartV3NewTcs.repository.CartRepo;
import com.example.AmitCartV3NewTcs.repository.CategoryRepo;
import com.example.AmitCartV3NewTcs.repository.ProductRepo;
import com.example.AmitCartV3NewTcs.repository.RoleRepo;
import com.example.AmitCartV3NewTcs.repository.UserRepo;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Component
public class DataLoader implements ApplicationRunner {
	
//	@Autowired
//	RoleRepo roleRepo;
	
	@Autowired
	UserRepo userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	//we have created this bean so directly can autowire in component classes
	//in Static methods,  can not autowire beans so not writting this code in main() method
	
	@Autowired
	CartRepo cartRepo;
	
	@Autowired
	CartProductRepo cartProductRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	CategoryRepo categoryRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
//		RoleModel role1 = roleRepo.save(new RoleModel("CONSUMER"));
//		RoleModel role2 = roleRepo.save(new RoleModel("SELLER"));
		
		Set<RoleModel> roleSellerCustomer = new HashSet<>();
//		roleSellerCustomer.add(role1);
//		roleSellerCustomer.add(role2);
		roleSellerCustomer.add(RoleModel.CONSUMER); //RoleModel.CONSUMER retirns RoleModel
		roleSellerCustomer.add(RoleModel.SELLER);
		//since Enum does not have @Entity so no roleRepo interface created so no need to save Roles in db separately
		
		Set<RoleModel> roleCustomer = new HashSet<>();
//		roleCustomer.add(role1);
		roleCustomer.add(RoleModel.CONSUMER);
		
		Set<RoleModel> roleSeller = new HashSet<>();
//		roleSeller.add(role2);
		roleSeller.add(RoleModel.SELLER);
		
		UserModel userModel1= userRepo.save(new UserModel("jack", passwordEncoder.encode("pass_word"), "jack@gmail.com", roleCustomer));
		UserModel userModel2= userRepo.save(new UserModel("bob", passwordEncoder.encode("pass_word") , "bob@gmail.com", roleCustomer));
		UserModel userModel3=userRepo.save(new UserModel("apple", passwordEncoder.encode("pass_word") , "apple@gmail.com", roleSeller));
		UserModel userModel4=userRepo.save(new UserModel("glaxo", passwordEncoder.encode("pass_word") , "glaxo@gmail.com", roleSeller));
		UserModel userModel5=userRepo.save(new UserModel("amit", passwordEncoder.encode("pass_word") , "amit@gmail.com", roleSellerCustomer));
		//these codes are working for both BCrypt and NoOp Password Encoders
		//userRepo.save(new UserModel("amit", "pass_word" , "amit@gmail.com", roleSellerCustomer));//if NoOpPasswordEncoder
		//above one also will work, it will just store password as it is
		//in postman simply pass unencrypted password, only in db, saving encrypted password

		
		//Category category1= categoryRepo.save(new Category("Fashion"));
		//loading Fashion from data.sql
		Category category2=categoryRepo.save(new Category("Electronics"));
		categoryRepo.save(new Category("Books"));
		categoryRepo.save(new Category("Groceries"));
		Category category5=categoryRepo.save(new Category("Medicines"));
		
		Cart cart1= cartRepo.save(new Cart(20.0, userModel1));
		Cart cart2= cartRepo.save(new Cart(0.0, userModel2));
		
		Product product1 = productRepo.save(new Product("Apple iPad 10.2 8th Gen WiFi iOS Tablet", 29190.0, userModel3, category2));
		Product product2 = productRepo.save(new Product("Crocin pain relief tablet", 10.0, userModel4, category5));
		
		cartProductRepo.save(new CartProduct(2, product2, cart2));
		//in cartProduct class:if writing cascade then in data loader giving error: detached entity passed to persist: 
		//use cascade=CascadeType.MERGE. no error with this
		//@ManyToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
		//@JoinColumn(name="product_id", referencedColumnName="productId")
		//private Product product;
		
		//data .sql is executed first then this data loader is executed
		//so if write insert into cart product code in data.sql, then error of refertial integrity constraint
		//means first insert FK then insert this

	}

}
