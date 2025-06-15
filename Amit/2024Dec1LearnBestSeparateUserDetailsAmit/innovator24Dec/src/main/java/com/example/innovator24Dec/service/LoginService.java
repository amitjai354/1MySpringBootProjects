package com.example.innovator24Dec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.innovator24Dec.entity.UserInfo;
import com.example.innovator24Dec.repository.UserInfoRepository;

import jakarta.servlet.http.HttpServletResponse;

@Component //required as autowiring below classes in this class also autowiring tyhis class at other places
public class LoginService {

	@Autowired
	private UserInfoRepository repository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//this method was given in exam but no signUp api given in LoginController
	//so it was giving 404 Not Found for signUp, this means either api name is wrong
	//or it does not exist
	
	//creted 201 return id but not password in response
	//nothing given about error message in exam paper
	public ResponseEntity<UserInfo> addUser(UserInfo userInfo){
		//this was SC_CREATED but i was giving OK 200 so test case fail.. 
		//test case fail has blue cross and test case error has red cross..
		//if test case pass then will not come in the screen
		//because of this all the test cases were failing..
		//because sign up was failed..LoginService so all apis giving no data
		//but when running from postman, all apis were successful.. because whiling signing up status was 200
		//so in test case no user inserted in the db as status expected was 201
		//but when manually doing... status did not matter.. on 200 also data saved in db table
		//so other apis worked fine
		
		try {
			//userInfo.setId((Integer)null); //id is int so can not use null here.. but latest hibernate requires null here
			userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
			userInfo = repository.save(userInfo);
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(userInfo);
		}
		catch (Exception e) {
			e.printStackTrace();
			//return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in sign up");
			//error can not convert from ResponseEntity<String> to  ResponseEntity<UserInfo>
			
			throw new BadCredentialsException("error in sign up " + e.getMessage());
			//if doing throw Exception then ask to add throws in method signature as Exception has both runtime and
			//compile time exceptions
		}
		
	}
}
