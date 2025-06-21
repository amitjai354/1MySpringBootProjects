package com.example.innovator24Dec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.innovator24Dec.entity.UserInfo;
import com.example.innovator24Dec.repository.UserInfoRepository;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class LoginService {

	@Autowired
	private UserInfoRepository repository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	//this method was given in exam but no signUp api given in LoginController
	//so it was giving 404 Not Found for signUp, this means either api name is wrong
	//or it does not exist
	public ResponseEntity<UserInfo> addUser(UserInfo userInfo) throws Exception{
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
			userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
			userInfo = repository.save(userInfo);
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(userInfo);
		}
		catch (Exception e) {
			// TODO: handle exception
			//throw new Exception("error in signUp " + e.getMessage());
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(new UserInfo());
		}
	}
}
