package com.example.Innovator24June.service;

import java.security.Key;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

//nest time in exam, if not working with Tcs code, delte all tcs coide if required delete class also
//write your own code.. you have question and you just have to run that

public class JwtService { //Jwt Utill class
	
	public  static final String SECRET="5367566B59783373367639792F423F$23F4528482B4D625165546";
	
	private Key getSignKey() {
		return null;
	}
	
	public <T> T extractClaim(String token, Function<T, Claims> claimResolver) {
		return null;
	}
	
	private Claims extractAllClaims(String token) {
		return null;
	}
	
	public String generateToken(String username) {
		return null;
	}
	
	private String createToken(Map<String, Object> claims, String username) {
		return null;
	}
	
	public Boolean isTokenExpired(String token) {
		return null;
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		return null;
	}
}
