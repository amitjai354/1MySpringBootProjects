package com.example.Dec2024AmitCartV3New.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.Dec2024AmitCartV3New.models.Cart;
import com.example.Dec2024AmitCartV3New.models.CartProduct;
import com.example.Dec2024AmitCartV3New.models.Category;
import com.example.Dec2024AmitCartV3New.models.Product;
import com.example.Dec2024AmitCartV3New.models.Role;
import com.example.Dec2024AmitCartV3New.models.User;
import com.example.Dec2024AmitCartV3New.repo.CartProductRepo;
import com.example.Dec2024AmitCartV3New.repo.CartRepo;
import com.example.Dec2024AmitCartV3New.repo.CategoryRepo;
import com.example.Dec2024AmitCartV3New.repo.ProductRepo;
import com.example.Dec2024AmitCartV3New.repo.UserRepo;

@Component
public class DataLoader implements ApplicationRunner {
	
	@Autowired
	CategoryRepo categoryRepo;
	
	@Autowired
	CartProductRepo cartProductRepo;
	
	@Autowired
	CartRepo cartRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	UserRepo userRepo;

	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Category category2 = categoryRepo.save(new Category("Electronics"));
		Category category3 = categoryRepo.save(new Category("Books"));
		Category category4 = categoryRepo.save(new Category("Groceries"));
		Category category5 = categoryRepo.save(new Category("Medicines"));
		
		Role roleC = Role.CONSUMER;
		Role roleS = Role.SELLER;
		
		Set<Role> roleCset = new HashSet<>();
		Set<Role> roleSset = new HashSet<>();
		Set<Role> roleCSset = new HashSet<>();
		
		roleCset.add(roleC);
		roleSset.add(roleS);
		
		roleCSset.add(roleC);
		roleCSset.add(roleS);
		
		User user1 = userRepo.save(new User("jack", passwordEncoder.encode("pass-word"), roleCset));
		User user2 = userRepo.save(new User("bob", passwordEncoder.encode("pass-word"), roleCset));
		User user3 = userRepo.save(new User("apple", passwordEncoder.encode("pass-word"), roleCset));
		User user4 = userRepo.save(new User("glaxo", passwordEncoder.encode("pass-word"), roleCset));
		User userA = userRepo.save(new User("amit", passwordEncoder.encode("pass-word"), roleCSset));
		
		//we have created category and user so now can create product and cart
		//once we create product and cart then only we can create cartProduct as it has both ids
		
		Product product1 = productRepo.save(new Product(1, "Apple iPad 10.2 8th Gen WiFi iOS Tablet", 29190.0, user3, category2));  
		Product product2 = productRepo.save(new Product(2, "Crocin pain relief tablet", 10.0, user4, category5));
		
		Cart cart1 = cartRepo.save(new Cart(20.0, user1));
		Cart cart2 = cartRepo.save(new Cart(0.0, user2));
		
		CartProduct cartProduct1 = cartProductRepo.save(new CartProduct(cart1, product2, 2));
		
	}

}
