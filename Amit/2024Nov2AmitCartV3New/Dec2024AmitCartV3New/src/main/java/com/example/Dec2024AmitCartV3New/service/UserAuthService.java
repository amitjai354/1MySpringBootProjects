package com.example.Dec2024AmitCartV3New.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Dec2024AmitCartV3New.models.User;
import com.example.Dec2024AmitCartV3New.repo.UserRepo;

@Service
public class UserAuthService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepo.findByUsername(username);
		if(user.isPresent()) {
			return user.get();
		}
		else {
			throw new UsernameNotFoundException("User Name not found");
		}
	}
	
	public User loadUserByUserId(Integer id) {
		Optional<User> user = userRepo.findById(id);
		if(user.isPresent()) {
			return user.get();
		}
		else {
			throw new UsernameNotFoundException("User Id not found");
			//for user id throwing user name exception
		}
	}

}
