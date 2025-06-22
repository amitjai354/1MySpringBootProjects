package com.example.Dec2024AmitCartV3New.config;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.Dec2024AmitCartV3New.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtill {
	
	@Value("${jwt.secret}")
	private String JWT_SECRET;
	
	@Value("${jwt.token.validity}")
	private long JWT_SECRET_VALIDITY;

	public String getUsernameFromToken(final String token) {
		return this.getClaimsFromToken(token, Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token) {
		return this.getClaimsFromToken(token, Claims::getExpiration);
	}
	
	public <T> T getClaimsFromToken(String token, Function<Claims, T> claimResolver) {
		Claims claims = this.getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
	}
	
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(this.getSignKey()).build().parseClaimsJws(token).getBody();
	}
	
	public Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return this.doGenerateToken(claims, username);
	}
	
	public String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+JWT_SECRET_VALIDITY))
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public Boolean isTokenExpired(String token) {
		Date expiration = new Date();
		return expiration.before(new Date());
	}
	
	public Boolean validateToken(final String token, UserDetails userDetails) {
		String username = this.getUsernameFromToken(token);
		return username.equals(userDetails.getUsername()) && (!this.isTokenExpired(token));
	}
		
}
