package com.example.innovator24Dec.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.innovator24Dec.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{
	//no method given here, add your method
	
	Optional<UserInfo> findByName(String name);
	
	//better write one service class: UserInfoService and here write userRepo.findByName(username).orElseThrow(()->new UsernameNotFoundException("username not found"));
	//now whenever needed just write userInfoService.getByUsername(username); //now no need to write orElseThrow everytime
	
	//in this project no such service class is given, can create one as it is always advised to not call repo class directly.. instead use user service
}
