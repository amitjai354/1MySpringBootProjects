package com.example.Innovator24June.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Innovator24June.entity.UserInfo;



public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{
	//no method given here, add your method
	Optional<UserInfo> findByName(String username);
	//here in model class we have name not username so be careful
}
