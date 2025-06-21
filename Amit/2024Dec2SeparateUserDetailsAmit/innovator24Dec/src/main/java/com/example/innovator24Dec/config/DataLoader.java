package com.example.innovator24Dec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.innovator24Dec.entity.Design;
import com.example.innovator24Dec.entity.UserInfo;
import com.example.innovator24Dec.repository.DesignRepository;
import com.example.innovator24Dec.repository.UserInfoRepository;

@Component
public class DataLoader implements ApplicationRunner{

	@Autowired
	UserInfoRepository userRepo;
	
	@Autowired
	DesignRepository designRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
		UserInfo userInfo1 = userRepo.save(new UserInfo("Dev", "dev@gmail.com", passwordEncoder.encode("pass1"), "DESIGNER"));
		UserInfo userInfo2 = userRepo.save(new UserInfo("John", "John@gmail.com", passwordEncoder.encode("pass2"), "DESIGNER"));
		UserInfo userInfo3 = userRepo.save(new UserInfo("Sam", "Sam@gmail.com", passwordEncoder.encode("pass3"), "USER"));

		Design design1 = designRepo.save(new Design("Stylish Backpack", "Durable and stylish backpack for everyday use.", "Olive Green", 990, "In Stock", "http://example.com/images/backpack.jpg", "One Size", 1));   
		Design design2 = designRepo.save(new Design("Casual Hoodie", "Comfortable hoodie for a relaxed day out.", "Black", 2490, "Out of Stock", "http://example.com/images/hoodie.jpg", "S, M, L, XL", 2));
	}

	//write content directly from test cases instead of typing each one by one
	//this will save time
	
	//in question paper table, it was written: Casual hoodie.. but in json and test case written: Casual Hoodie
	//this is handled by ignoreCase but space issue is not handled..
	//in paper, S, M, L,XL but in test case S, M, L, XL   so this space thing can not ignore..
	//so if in my db stored : S, M, L,XL  
	//but in get api when try to match it has: S, M, L, XL 
	//so test case will fail... even after doing everything right..
	
	
	//so it is best to write long lines from test cases directly.. instead of typing each thing manuallly.. it took
	//my lots of time.. 
	
	//do not forget this.. must open test cases.. you do not open test cases ever in exam hall.. first thing is read
	//paper and then open test cases..
	
	//copy paste here from test cases...
	
	
}
