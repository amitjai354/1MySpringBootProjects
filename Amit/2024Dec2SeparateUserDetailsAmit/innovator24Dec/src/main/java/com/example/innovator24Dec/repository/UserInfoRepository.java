package com.example.innovator24Dec.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.innovator24Dec.entity.UserInfo;
import java.util.List;


@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{
	//no method given here, add your method
	
	Optional<UserInfo> findByName(String name);
	
	
}
