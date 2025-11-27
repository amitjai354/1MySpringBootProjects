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
public class DataLoader implements ApplicationRunner{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private CartRepo cartRepo;
	
	@Autowired
	private CartProductRepo cartProductRepo;
	

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Role consumerRole = Role.CONSUMER;
		Role sellerRole = Role.SELLER;
		
//		Role consumerRole = roleRepo.save(new Role(1, "CONSUMER"));
//		Role sellerRole = roleRepo.save(new Role(1, "SELLER"));
		
		//first save those models which does have any foreign key attribute in them
		//otherwise constraint exception while saving that save that first then save this
		//Category category1 = categoryRepo.save(new Category("Fashion")); 
		//saving Fashion category using data.sql from resources folder
		//First data will be inserted from data.sql then this data loader will run so Fashion will get id 1
		//Electronics will get id 2
		Category category2 = categoryRepo.save(new Category("Electronics"));
		Category category3 = categoryRepo.save(new Category("Books"));
		Category category4 = categoryRepo.save(new Category("Groceries"));
		Category category5 = categoryRepo.save(new Category("Medicines"));
		
		//Product has both seller and category so need to save user first
		//user has role only so can save user as already saved role.. here no need to save role as role is enum
		//User and Role has many to many relation.. so separate table user role created..
		//in this new table check what is the role of user
		//here constructor of User is taking Set<Role> to create user so create set of role first
		
		Set<Role> consumerRoleSet = new HashSet<>();
		Set<Role> sellerRoleSet = new HashSet<>();
		Set<Role> consumerSellerRoleSet = new HashSet<>();
		
		consumerRoleSet.add(consumerRole);
		sellerRoleSet.add(sellerRole);
		
		consumerSellerRoleSet.add(consumerRole);
		consumerRoleSet.add(sellerRole);
		
		User user1 = userRepo.save(new User("jack", passwordEncoder.encode("pass_word"), consumerRoleSet));
		User user2 = userRepo.save(new User("bob", passwordEncoder.encode("pass_word"), consumerRoleSet));
		User user3 = userRepo.save(new User("apple", passwordEncoder.encode("pass_word"), sellerRoleSet));
		User user4 = userRepo.save(new User("glaxo", passwordEncoder.encode("pass_word"), sellerRoleSet));
		User user5 = userRepo.save(new User("amit", passwordEncoder.encode("pass_word"), consumerSellerRoleSet));
		
		//we have created category and user so now can create product and cart
		//once we create product and cart then only we can create cartProduct as it has both ids
		
		
		//now we can create product as we have both seller and category
		Product product1 = productRepo.save(new Product(1, "Apple iPad 10.2 8th Gen WiFi iOS Tablet", 29190.0, user3, category2));
		Product product2 = productRepo.save(new Product(2, "'Crocin pain relief tablet", 10.0, user4, category5));
		
		//cart has cartProduct and cartProduct has cart .. but to create any one of them should be available fist
		//thing is code is written in such a way that we are not creating cartProductId column in cart.. by using 
		//mappedBy in join column
		//we are creating cartId column in CartProduct.. so create cart first
		
		//Here we will pass complete user1 and in foreign key we will see userid in sql automatically
		//this will be done by mapping that we do like one to one or many to one or one to many or many to many
		Cart cart1 = cartRepo.save(new Cart(20.0, user1));
		Cart cart2 = cartRepo.save(new Cart(0.0, user2));
		
		CartProduct cartProduct1 = cartProductRepo.save(new CartProduct(cart1, product2, 2));
		
	}

}
