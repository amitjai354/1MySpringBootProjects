package com.example.Innovator24June.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Innovator24June.entity.UserInfo;



@Service
public class LoginService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	public ResponseEntity addUser(UserInfo userInfo) {
		return null;
	}
}
