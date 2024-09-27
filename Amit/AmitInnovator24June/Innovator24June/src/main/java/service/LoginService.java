package service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import entity.UserInfo;
import repository.UserInfoRepository;

@Service
public class LoginService {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	UserInfoRepository userInfoRepository;

	public ResponseEntity addUser(UserInfo userInfo) {
		return null;
	}
	
//	public UserInfo getUserByUsername(String username) {
//		return userInfoRepository.findByName(username).orElseThrow(()->new UsernameNotFoundException("invalid username"));
//		
////		Optional<UserInfo> userInfo = userInfoRepository.findByName(username);
////		if(userInfo==null) {
////			throw new UsernameNotFoundException("invalid username");
////		}
////		return userInfo;
//		
//		//in load user by username .. in exam already autowired repository there so can directly write there..
//		//I had created thois method here..
//	}
}
