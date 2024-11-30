package com.example.Innovator24June.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Innovator24June.entity.UserInfo;
import com.example.Innovator24June.repository.UserInfoRepository;

import jakarta.servlet.http.HttpServletResponse;



@Service
public class LoginService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserInfoRepository userInfoRepository;

	public ResponseEntity addUser(UserInfo userInfo) {
		try {
			userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
			userInfo = userInfoRepository.save(userInfo);
			return ResponseEntity.status(HttpServletResponse.SC_CREATED).body(userInfo);
		}
		catch (Exception e) {
			return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("error in saving user");
		}
	}
}
