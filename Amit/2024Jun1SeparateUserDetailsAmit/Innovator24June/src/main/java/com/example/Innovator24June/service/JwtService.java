package com.example.Innovator24June.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

//nest time in exam, if not working with Tcs code, delte all tcs coide if required delete class also
//write your own code.. you have question and you just have to run that

@Service
public class JwtService { //Jwt Utill class
	
	@Value("${jwt.secret}")
	private String JWT_SECRET;
	
	public  static final String SECRET="5367566B59783373367639792F423F23F4528482B4D625165546";
	//had written $ in Secret here.. was giving error in decoding password.. even if no $ in password
	final long JWT_TOKEN_VALIDITY=5*60*60*1000;
	
	private Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		//byte[] keyBytes = this.SECRET.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String getUsernameFromToken(String token) {
		return this.extractClaim(token, Claims::getSubject);
	}
	
	public Date getExpirationDateFromToken(String token) {
		return this.extractClaim(token, Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		//i had written <T,claims> so be carefull
		final Claims claims = this.extractAllClaims(token);
		return claimResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {	
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();//no deprecation in 0.11.5
		//return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();//no deprecation in 0.12.5
		
		//return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();//deprecated parser and signing key in 0.11.5 itself
		//return Jwts.parser().setSigningKey(this.getSigningKey()).parseClaimsJws(token).getBody();//parser and setSignKey deprecated in 0.11.5
		//return Jwts.parser().parseClaimsJws(token).getBody();//parser is deprecated use parseBuilder instead say
        //return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();//no deprecated here in 0.11.5 and generating token with long string
        //parserBuilder and parseClaimsJws not present in 0.12.5 here verifyWith only verifyWith not present in 0.11.5
        //return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();//verify with not present in 0.11.5
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
				.signWith(this.getSigningKey())
				//.build() this is not build here.. this is compact() in both 0.11.5 and 0.12.5
				.compact();
	}
	
	public Boolean isTokenExpired(String token) {
		Date expiration = this.getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = this.getUsernameFromToken(token);
		return username.equals(userDetails.getUsername())&&(!this.isTokenExpired(token));
	}
}
