package com.example.innovator24Dec.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public class JwtService {
	
	//write secret given in exam
	public static final String SECRET = "";
	
	public static final long JWT_TOKEN_VALIDITY = 500*60*60; //given in exam.. 1800000ms = 1800s = 30 min
	
	public String extractUsername(String token) {
		return null;
	}
	
	//java.util.Date; not from sql date
	public Date extractExpiration(String token) {
		return null;
	}
	
	//in previous papers: java.util.function.Function;
	public <T> T extractClaim(String token, Function<Claims, T> claimResolver ) {
		return  null;
	}
	
	private Claims extractAllClaims(String token) {
		return null;
	}
	
	private Boolean isTokenExpired(String token) {
		return null;
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		return null;
	}
	
	public String generateToken(String username) {
		return null;
	}
	
	private String createToken(Map<String, Object> claims, String username) {
		return null;
	}
	
	//java.awt.RenderingHints.Key or java.security.Key;
	//in previous papers:java.security.Key; and io.jsonwebtoken.security.Keys; 
	private Key getSignKey() {
		return null;
	}

}
