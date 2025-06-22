package com.example.Dec2024AmitCartV3New.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.example.Dec2024AmitCartV3New.models.Category;
import com.example.Dec2024AmitCartV3New.repo.CartProductRepo;
import com.example.Dec2024AmitCartV3New.repo.CartRepo;
import com.example.Dec2024AmitCartV3New.repo.CategoryRepo;
import com.example.Dec2024AmitCartV3New.repo.ProductRepo;

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

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		Category category2 = categoryRepo.save(new Category("Electronics"));
		Category category3 = categoryRepo.save(new Category("Books"));
		Category category4 = categoryRepo.save(new Category("Electronics"));
		Category category5 = categoryRepo.save(new Category("Electronics"));
		
	}

}
