package com.example.innovator24Dec.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	
	//next time in exam, if not working with Tcs code, delete all tcs code if required delete class also
	//write your own code.. you have question and you just have to run that
	
	
	//@Value("${jwt.secret}")
	//private String JWT_SECRET; //can not use final here error that JWT_SECRET might not be initalised, must initialise here
	
	//write secret given in exam
	public static final String SECRET = "5367566B5970337336762F423F4528482B4D6251655468576D5A71347437";
	
	public static final long JWT_TOKEN_VALIDITY = 500*60*60; //given in exam.. 1800000ms = 1800s = 30 min
	//if writing 5*60*60 then expiring immediately so write 5*60*60*1000 but above we have 500
	
	public String extractUsername(String token) {
		return this.extractClaim(token, Claims::getSubject);
	}
	
	//java.util.Date; not from sql date
	public Date extractExpiration(String token) {
		return this.extractClaim(token, Claims::getExpiration);
	}
	
	//in previous papers: java.util.function.Function;
	public <T> T extractClaim(String token, Function<Claims, T> claimResolver ) {
		Claims claims = this.extractAllClaims(token);
		return  claimResolver.apply(claims);
	}
	
	private Claims extractAllClaims(String token) {
		//was getting unable to parse Jwt token error in sign with..  
		//it was saying issue in signWith in create token
		//but issue was instead of parseClaimsJws i had imported parseClaimsJwt
		
		//UnSupportedJwtWxception: Signed Claims JWSs are not supported
		//i always read 1st line only but if i would have read log in detail, they had given issue was 
		//immutable parser parseClaimJwt in extractAllClaims()
		//they had given method name as well where error was in log
		return Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();
	}
	
	private Boolean isTokenExpired(String token) {
		Date expiration = this.extractExpiration(token);
		return expiration.before(expiration);
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = this.extractUsername(token);
		return username.equals(userDetails.getUsername()) && (!(this.isTokenExpired(token)));
	}
	
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return this.createToken(claims, username);
		
		//in 2023 exam, gave email everwhere, instead of username
		//issue is jwtRequest has email not username, loaduserbyusername has email not username 
		//and userModel has both username and email and both are unique
		
		//UserModel userModel = (UserModel) userDetails; earlier used to this as userModel implements UserDetails
		//but now separate class for userDetails so can not do this. use userRepo now
		//can not use loadUserByUsername(String email) as userDetails does not have email and 
		//this returns userDetails but we already have userDetails
		//so use findByUsername or findByEmail as per need
		
		//UserModel userModel = userRepo.findByUsername(JWT_SECRET).orElseThrow(()->new UsernameNotFoundException("Username not found in jwtService"));
		
		
		//return this.doGenerateToken(claims, userDetails.getUsername());
		
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
	
	private String createToken(Map<String, Object> claims, String username) {
		//all test cases passing with this
		//.signWith(SignatureAlgorithm.HS256, getSignKey())//deprecated
		//this was given in this exam..  
		
		return Jwts.builder()
				.claims(claims)
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
				//.signWith(getSignKey(), SignatureAlgorithm.HS256) //deprecated in jwt 0.12.6, 0.12.5 as well
				//.signWith(SignatureAlgorithm.HS256, getSignKey()) //0.12.6, 0.12.5 as well
				//.signWith(getSignKey()) //not deprecated
				.signWith(getSignKey(), Jwts.SIG.HS256) //this one is not deprecated in 0.12.6 jwt
				.compact();
	}
	
	//java.awt.RenderingHints.Key or java.security.Key;
	//in previous papers:java.security.Key; and io.jsonwebtoken.security.Keys; 
	private SecretKey getSignKey() {
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
		
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
