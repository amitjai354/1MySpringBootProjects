package com.example.Innovator2025DecemberMostImp.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

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
		Claims claims = this.exctractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	private Claims exctractAllClaims(String token) {
		//return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}
	
	private Boolean isTokenExpired(String token) {
		//if make return type as boolean, can not return null as Boolean can be null but primitive data types can not be
		Date expiration = this.extractExpiration(token);
		return expiration.before(new Date());
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = this.extractUsername(token);
		return username.equals(userDetails.getUsername()) && (! this.isTokenExpired(token));
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
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
}
