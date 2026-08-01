package com.example.Innovator2025DecemberMostImp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Innovator2025DecemberMostImp.entity.UserInfo;
import com.example.Innovator2025DecemberMostImp.repository.UserRepository;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserInfo userInfo = repository.findByName(username).orElseThrow(()->new UsernameNotFoundException("username not found"));
		return new UserInfoUserDetails(userInfo);
	}

}
