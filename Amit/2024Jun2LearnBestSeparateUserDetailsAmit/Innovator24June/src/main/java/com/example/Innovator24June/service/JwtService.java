package com.example.Innovator24June.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;
import javax.xml.crypto.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

//next time in exam, if not working with Tcs code, delte all tcs coide if required delete class also
//write your own code.. you have question and you just have to run that

@Service
public class JwtService { //Jwt Utill class
	
	@Value("${jwt.secret}")
	private String JWT_SECRET;
	
	//public static final String SECRET="5367566B59783373367639792F423F$23F4528482B4D625165546";
	public final long JWT_TOKEN_VALIDITY = 5*60*60*1000; //in exam given 500*60*60 that is around 30 min
	//if do not write 1000, token is expiring immediately
	
	public String getUsernameFromToken(String token) {
		return this.extractClaim(token, Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token) {
		return this.extractClaim(token, Claims::getExpiration);
	}
	
	private SecretKey getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);//this one was given in the exam.. 
		//String should be Base64 decode
		//byte[] keyBytes = this.JWT_SECRET.getBytes(StandardCharsets.UTF_8);//this one supports $ as well in SECRET
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		Claims claims = this.extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
		//return Jwts.parser().verifyWith(getSignKey()).build().parseSignedCLaims(token).getPayload();
	}
	
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return this.createToken(claims, username);
	}
	
	private String createToken(Map<String, Object> claims, String username) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
				.signWith(getSignKey(), SignatureAlgorithm.HS256)//all test cases passing with this
				//.signWith(SignatureAlgorithm.HS256, getSignKey())//deprecated
				//this was given in this exam..  i missed only this line.. was getting unable to parse Jwt token
				//error in sign with.. bas ye kar deta ho jata..
				//they had not given signUp in controller, so 404 not found.. 
				//had to create by mysel in LoginControler,, but was given addUser() in LoginService
				.compact();
	}
	
	public Boolean isTokenExpired(String token) {
		Date expiration = this.getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = this.getUsernameFromToken(token);
		return username.equals(userDetails.getUsername())&&(!(this.isTokenExpired(token)));
	}
}
