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
import com.fasterxml.jackson.annotation.JsonProperty;


@Component //needed as not autowiring this class at other place but autowiring other classes in this class
public class DataLoader implements ApplicationRunner {
	
	@Autowired
	UserInfoRepository userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	DesignRepository designRepo;

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
	
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		
		//error  org.springframework.orm.ObjectOptimisticLockingFailureException: 
		//Row was updated or deleted by another transaction (or unsaved-value mapping was incorrect):
		//[com.example.innovator24Dec.entity.UserInfo#1]
		
		//when i looked log in details... it was mentioned dataLoader line 51..
		//when came here, all user ids were 1 .. copy pate error but comp;ete strange error..
		//please be care full while writing.. can not resolve error at end
		
		//so first generate token then later do api code..
		//even first generate token then write data loader then write api codes if possible

		
		//if passing user id 1 in user id here, giving error row was updated or deleted..
		//there is no such constructor in User and Design class where id is not in constructor.. i had created these constructors myself in exam as well
		
		
		/*
		this error often arises when:
		The ID field in your entity is annotated with @GeneratedValue, which means Hibernate is responsible for generating the ID when the entity is persisted.
		The ID field in your DAO or Request object is explicitly set to a non-null value (e.g., a default value like 1). When you pass a request object 
		with a pre-defined ID to your service layer, Hibernate interprets this as an attempt to update an existing entity rather than create a new one. 
		If Hibernate cannot find an entity with that ID in the database, it throws the error because it assumes the row was either updated or deleted by another transaction.
		this came in Hibernate 6.6+ - - spring <version>3.5.0</version> in my created paper
		//in exam spring 3.3.4 given -- Hibernate 6.5.3.
		
		solution is either pass id as null
		or create new constructor that does not accept id, so do not pass id
		
		but issue is here in test cases, passing id in json.. change test cases then..make id null there..
		or best is in your signUp api, make id as null then save
		 */
		
		//i checked working in spring 3.3.4 but not 3.5.0
		//UserInfo userInfo1 = userRepo.save(new UserInfo(1, "Dev", "dev@gmail.com", passwordEncoder.encode("pass1"), "DESIGNER"));
		
		//all test cases passed in 3.3.4 with passing id as well
		//DaoAuthenticationProvider is also changed
		
		//	@JsonProperty(value = "designId", access = JsonProperty.Access.READ_ONLY)//can read get() but can not write set()
		//with this all test cases passed in latest spring 3.5.0
		
		UserInfo userInfo1 = userRepo.save(new UserInfo("Dev", "dev@gmail.com", passwordEncoder.encode("pass1"), "DESIGNER"));
		UserInfo userInfo2 = userRepo.save(new UserInfo("John", "john@gmail.com", passwordEncoder.encode("pass2"), "DESIGNER"));
		UserInfo userInfo3 = userRepo.save(new UserInfo("Sam", "sam@gmail.com", passwordEncoder.encode("pass3"), "USER"));
		
		
		Design design1 = designRepo.save(new Design("Stylish Backpack", "Durable and stylish backpack for everyday use.", "Olive Green", 990, "In Stock", "http://example.com/images/backpack.jpg", "One Size", userInfo1.getId()));
		Design design2 = designRepo.save(new Design("Casual Hoodie", "Comfortable hoodie for a relaxed day out.", "Black", 2490, "Out of Stock", "http://example.com/images/hoodie.jpg", "S, M, L, XL", userInfo2.getId()));
		//check by removing one space from here in size as on copying from exam instead of test case, i had :
		// S, M, L,XL  no spcase before XL

	}
	
}
