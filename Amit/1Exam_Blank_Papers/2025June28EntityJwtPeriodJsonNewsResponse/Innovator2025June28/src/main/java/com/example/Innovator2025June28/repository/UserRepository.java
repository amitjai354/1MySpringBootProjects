package com.example.Innovator2025June28.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.Innovator2025June28.entity.UserInfo;

import jakarta.transaction.Transactional;

public interface UserRepository extends JpaRepository<UserInfo, Integer> {
	
	
	
	

	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "insert into user_info(name, email, password, roles) values(?1, ?2, ?3, ?4)")
	void insertUser(String name, String email, String password, String roles);//copy paste inputs from UserInfo constructor
	
	
	@Transactional
	@Modifying
	@Query(nativeQuery = true, value = "insert into user_info(name, email, password, roles) values(:name, :email, :password, :roles)")
	void insertUser1(@Param("name") String name, @Param("email") String email, @Param("password") String password, @Param("roles") String roles);   
	
	
}
