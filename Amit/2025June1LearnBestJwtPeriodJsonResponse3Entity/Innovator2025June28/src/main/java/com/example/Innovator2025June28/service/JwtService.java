package com.example.Innovator2025June28.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	//token is saved in temp.txt file: so if any token error, check here once
	//this file is present pom.xml in project
	//in test case they have written code to create this file and save token then get token from here
	
	//{"token_admin_2":"eyJhbGciOiJub25lIn0.eyJzdWIiOiJEZXYiLCJpYXQiOjE3NTEyOTg2MzMsImV4cCI6MTc1MTMwMDQzM30.",
	//"token_admin_1":"eyJhbGciOiJub25lIn0.eyJzdWIiOiJEZXYiLCJpYXQiOjE3NTEyOTg2MzMsImV4cCI6MTc1MTMwMDQzM30.",
	//"token_user_1":"eyJhbGciOiJub25lIn0.eyJzdWIiOiJTYW0iLCJpYXQiOjE3NTEyOTg2MzMsImV4cCI6MTc1MTMwMDQzM30."}
	
	//eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJEZXYiLCJpYXQiOjE3NTExMDA0NzIsImV4cCI6MTc1MTEwMjI3Mn0.Z-lvYMTUG0LWgPzAxgAiv438zDAIb9ae1OR0uPYSowk
	//this token is generated in my previous papers manually in postman
	//above there should be something after . at the end

	//use the SECRET key which is provided in problem statement
	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A74579987";
	
	private static final long JWT_TOKEN_VALIDITY = 500*60*60; 
	//in exam given token valid for 30 min mean 500*60*60 = 1800,000ms ==1800s = 30min
	
	public String extractUsername(String token) {
		return this.extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return this.extractClaim(token, Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T>claimResolver) {
		Claims claims = this.exctractAllClaims(token);//here a bit confusion that what to write
		return claimResolver.apply(claims);
	}
	
	private Claims exctractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(this.getSignKey()).build().parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		//if make return type as boolean, can not return null as Boolean can be null but primitive data types can not be
		Date expiration = this.extractExpiration(token);
		return expiration.before(new Date());//here i got confused for new date that what to wrtite
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = this.extractUsername(token);
		return username.equals(userDetails.getUsername()) && (!this.isTokenExpired(token));
	}
	
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return this.createToken(claims, username);
	}
	
	public String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
				.compact();
	}
	
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
}
