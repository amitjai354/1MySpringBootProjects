package com.example.innovator24Dec.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public class JwtService {
	
	//next time in exam, if not working with Tcs code, delete all tcs code if required delete class also
	//write your own code.. you have question and you just have to run that
	
	
	//@Value("${jwt.secret}")
	//private String JWT_SECRET;
	
	//write secret given in exam
	public static final String SECRET = "";
	
	public static final long JWT_TOKEN_VALIDITY = 500*60*60; //given in exam.. 1800000ms = 1800s = 30 min
	//if writing 5*60*60 then expiring immediately so write 5*60*60*1000 but above we have 500
	
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
		//was getting unable to parse Jwt token error in sign with..  
		//it was saying issue in signWith in create token
		//but issue was instead of parseClaimsJws i had imported parseClaimsJwt
		
		//UnSupportedJwtWxception: Signed Claims JWSs are not supported
		//i always read 1st line only but if i would have read log in detail, they had given issue was 
		//immutable parser parseClaimJwt in extractAllClaims()
		//they had given method name as well where error was in log
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
		//all test cases passing with this
		//.signWith(SignatureAlgorithm.HS256, getSignKey())//deprecated
		//this was given in this exam..  
		
		
		return null;
	}
	
	//java.awt.RenderingHints.Key or java.security.Key;
	//in previous papers:java.security.Key; and io.jsonwebtoken.security.Keys; 
	private Key getSignKey() {
		//it was given in the exam that String should be Base64 decode HS256 encrpted
		//ensure that claims in key should be as follows
		//HEADER{ "alg":"HS256", "typ":"JWT" }
		//PAYLOAD{ "sub":"Dev", (username)
		// "iat":1576071104408, (time of creation in millisecond)
		// "exp":1576072904408, (expiry time in millisecond, each token is valid for 30 min)}
		//SIGNATURE should be signed with secret key encoded in base64
		
		//we already do : setClaims, setSubject, setIssuedAt, setExpiration, signWith here can 
		//define HS256 or whatever needed.. but by default HS256 encrypted.. 
		//so we already are doing everything
		
		
		
		//byte[] keyBytes = this.JWT_SECRET.getBytes(StandardCharsets.UTF_8);//this one supports $ as well in SECRET
		return null;
	}

}
