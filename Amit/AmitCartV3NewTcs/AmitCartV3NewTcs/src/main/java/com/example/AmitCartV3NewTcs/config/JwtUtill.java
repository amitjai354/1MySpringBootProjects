package com.example.AmitCartV3NewTcs.config;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import java.util.function.Function;
import io.jsonwebtoken.impl.lang.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.AmitCartV3NewTcs.model.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtill {
	
	private String mySecretKey="randomKey123E12345fhjgERYUWRER6876yufhjwiw7yyt6tgjhdjh";
	final private long JWT_TOKEN_VALIDITY=5*60*60*1000;

	public String getUsernameFromToken(String token) {
		return this.getClaimFromToken(token, Claims::getSubject);
	}
	
	public Date getExpirationFromToken(String token) {
		return this.getClaimFromToken(token, Claims::getExpiration);
		//date is java.util.date not sql date
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
		//Function io.jsonwebtoken.impl.lang.Function; or java.util.function.Function;
		//working with both, generating token
		final Claims claims = this.getAllClaimFromToken(token);
		return claimResolver.apply(claims);
	}
	
	private Claims getAllClaimFromToken(String token) {
		//return Jwts.parser().setSigningKey(mySecretKey).parseClaimsJws(token).getBody();
		return Jwts.parser().verifyWith(this.getSigningKey()).build().parseSignedClaims(token).getPayload();
	}
	
	public SecretKey getSigningKey() {
		//javax.crypto.SecretKey;
		byte[] keyBytes= Decoders.BASE64.decode(mySecretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		UserModel userModel = (UserModel) userDetails;
		return this.doGenerateToken(claims, userModel.getEmail());
	}
	
	public String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.claims(claims)
				.subject(subject)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
				.signWith(getSigningKey())
				//.signWith(SignatureAlgorithm.HS256, mySecretKey);
				.compact();
	}
	
	public Boolean isTokenExpired(String token) {
		Date expiration = this.getExpirationFromToken(token);
		return expiration.before(new Date());
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = this.getUsernameFromToken(token);
		UserModel userModel = (UserModel) userDetails;
		return (username.equals(userModel.getEmail()) && !this.isTokenExpired(token));
	}
	
}
