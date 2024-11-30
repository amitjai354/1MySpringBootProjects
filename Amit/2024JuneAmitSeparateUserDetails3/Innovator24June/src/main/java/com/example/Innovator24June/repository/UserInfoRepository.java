package com.example.Innovator24June.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Innovator24June.entity.UserInfo;
import java.util.List;
import java.util.Optional;




public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>{
	//no method given here, add your method
	
	Optional<UserInfo> findByName(String name);
}
