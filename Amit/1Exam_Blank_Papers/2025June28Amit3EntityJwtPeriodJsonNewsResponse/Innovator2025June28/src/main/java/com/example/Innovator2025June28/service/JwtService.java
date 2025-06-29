package com.example.Innovator2025June28.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public class JwtService {

	//use the SECRET key which is provided in problem statement
	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A74579987";
	
	private static final long JWT_TOKEN_VALIDITY = 0; 
	//in exam given token valid for 30 min mean 500*60*60 = 1800,000ms ==1800s = 30min
	
	public String extractUsername(String token) {
		return null;
	}
	
	public Date extractExpiration(String token) {
		return null;
	}
	
	public <T> T extractClaim(String token, Function<Claims, T>claimResolver) {
		return null;
	}
	
	private Claims exctractAllClaims(String token) {
		return null;
	}
	
	private Boolean isTokenExpired(String token) {
		//if make return type as boolean, can not return null as Boolean can be null but primitive data types can not be
		return null;
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		return null;
	}
	
	public String generateToken(String username) {
		return null;
	}
	
	public String createToken(Map<String, Object> claims, String username) {
		return null;
	}
	
	private Key getSignKey() {
		return null;
	}
	
}
