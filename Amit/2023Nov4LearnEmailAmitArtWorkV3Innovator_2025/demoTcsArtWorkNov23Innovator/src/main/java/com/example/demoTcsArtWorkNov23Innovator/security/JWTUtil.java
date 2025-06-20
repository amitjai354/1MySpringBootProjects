package com.example.demoTcsArtWorkNov23Innovator.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demoTcsArtWorkNov23Innovator.model.UserModel;
import com.example.demoTcsArtWorkNov23Innovator.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JWTUtil {
	
	@Autowired
	UserRepository userRepo;
	
	@Value("${jwt.secret}")
	private String JWT_SECRET; //can not make final here
 
	final long JWT_TOKEN_VALIDITY = 500*60*60;
	
	public String getUsernameFromToken(String token) {
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
		return Jwts.parser().verifyWith(this.getSignKey()).build().parseSignedClaims(token).getPayload();
		//return Jwts.parserBuilder().setSigningKey(this.getSignKey()).build().parseClaimsJws(token).getBdy();
	}
	
	public SecretKey getSignKey(){
		byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}
	
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		
		//issue is jwtRequest has email not username, loaduserbyusername has email not username 
		//and userModel has both username and email and both are unique
		
		//UserModel userModel = (UserModel) userDetails; earlier used to this as userModel implements UserDetails
		//but now separate class for userDetails so can not do this. use userRepo now
		//can not use loadUserByUsername(String email) as userDetails does not have email and 
		//this returns userDetails but we already have userDetails
		//so use findByUsername or findByEmail as per need
		UserModel userModel = userRepo.findByUsername(JWT_SECRET).orElseThrow(()->new UsernameNotFoundException("Username not found in jwtService"));
		
		
		return this.doGenerateToken(claims, userDetails.getUsername());
		//return this.doGenerateToken(claims, userModel.getEmail()); earlier we were doing this but no need
		//as in login api, jwtRequest has email, that we pass in authToken to authenticate
		//then we call loadUserByusename to get userdetails that we can do with email here
		//then we call genarateToken(userDetails), no problem here
				
		//in jwtFilter code, we get token form token header then getUsernameFromToken()
		//now we need email to call loadUserByusername to get userdetails as validateToken(token, userDetails)
		//so now here , we can user repo to call findUserByUsername, this userModel will have email
		//earlier in jwtfilter, we were directly doing getUsernamefromtoken and then directly getting email
		
		//Basically we have to get email when we call getUsernameFromToken, that we can do by calling
		//findByUsername once we get username from getUsernameFromToken
	}
	
	public String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.claims(claims)
				.subject(subject)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
				.signWith(getSignKey(), Jwts.SIG.HS256)
				//.signWith(getSignKey(), SignatureAlgorithm.HS256)//deprecated in 0.12.5
				//.signWith(SignatureAlgorithm.HS256, getSignKey())//deprecated
				.compact();
	}
	
	public Boolean isTokenExpired(String token) {
		Date expiration = this.getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = this.getUsernameFromToken(token);
		return username.equals(userDetails.getUsername()) && (!this.isTokenExpired(token));
	}
}
