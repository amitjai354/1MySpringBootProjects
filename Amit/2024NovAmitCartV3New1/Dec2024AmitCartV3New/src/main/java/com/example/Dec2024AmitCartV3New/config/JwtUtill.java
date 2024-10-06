package com.example.Dec2024AmitCartV3New.config;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.Dec2024AmitCartV3New.models.User;
import com.example.Dec2024AmitCartV3New.service.UserAuthService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtill {
	
	@Autowired
	private UserAuthService userAuthService;
	
	//try to get these values from application.properties configuration
	final long JWT_TOKEN_VALIDITY = 5*60*60*1000;
	final String SECRET = "12345566769dsfhfjhdgtsfasdfasensljkdshQ453546548697874632";

	public User getUser(final String token) {
		String username = this.getUsernameFromToken(token);
		User myUser = (User) userAuthService.loadUserByUsername(username);
		return myUser;
	}
	
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return this.doGenerateToken(claims, username);
	}
	
	public Boolean validateToken(final String token) {
		//here return type is void
		String username = this.getUsernameFromToken(token);
		User myUser = this.getUser(token);
		Boolean validateToken = (username.equals(myUser.getUsername())&&(!this.isTokenExpired(token)));
		return validateToken;
	}
	
	//only above 3 methods given in exam
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
		final Claims claims = this.getAllClaimsFromToken(token);
		return claimResolver.apply(claims);
	}
	
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder().setSigningKey(this.getSigningKey()).build().parseClaimsJws(token).getBody();
	}
	
	public SecretKey getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String getUsernameFromToken(String token) {
		return this.getClaimFromToken(token, Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token) {
		return this.getClaimFromToken(token, Claims::getExpiration);
	}
	
	public String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
				.signWith(getSigningKey())
				.compact();
	}
	
	public Boolean isTokenExpired(String token) {
		Date expiration = this.getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
}
