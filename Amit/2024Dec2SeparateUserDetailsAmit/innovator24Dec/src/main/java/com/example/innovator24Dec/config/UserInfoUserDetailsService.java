package com.example.innovator24Dec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.innovator24Dec.entity.UserInfo;
import com.example.innovator24Dec.repository.UserInfoRepository;


//this class will be autowired in security configuration class and also we will autowire repo class here so use @Component

@Service
public class UserInfoUserDetailsService implements UserDetailsService{

	@Autowired
	private UserInfoRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserInfo userInfo = repository.findByName(username).orElseThrow(()-> new UsernameNotFoundException("username not found"));  
		return new UserInfoUserDetails(userInfo);
	}

}
