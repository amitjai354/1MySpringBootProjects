package com.example.demoTcsArtWorkNov23Innovator.security;

import com.example.demoTcsArtWorkNov23Innovator.model.UserModel;
import com.example.demoTcsArtWorkNov23Innovator.repository.UserRepository;
import com.example.demoTcsArtWorkNov23Innovator.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTUtil implements Serializable {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;

    public static final long JWT_TOKEN_VALIDITY=500*60*60*1000;//5*60*60*1000
    String secretKey="r$andomkey123tooShortKeyE1234567898ABHGVVNgfdmhjn475877988";
    //String secretKey="andomkey1234261656C64756E67";//in 0.11.5 generated token but not in 0.12.5
    //String secretKey="randomkey123";//working with this as well in 0.11.5 but in DoGenerate method need to comment sign with line completely
    //working with this always even with parser builder just comment sign with in do generate
    //if write signWith then sayoing 72 bit less than 256 bit in 0.11.5 also Durgesh also said that keep string large
    //The JWT JWA Specification (RFC 7518, Section 3.2) states that keys used with HMAC-SHA algorithms
    // MUST have a size >= 256 bits (the key size must be greater than or equal to the hash output size).
    // Consider using the Jwts.SIG.HS256.key() builder (or HS384.key() or HS512.key()) to create a key
    // guaranteed to be secure enough for your preferred HMAC-SHA algorithm.
    //error when login api if secret key is too short.. it shoyld be 256 bit, but randomKey123 is 72 bit
    //is there any security where this short one will work.. there is one 64 bit as well

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
    	return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }
    
    public SecretKey getSigningKey() {
    	//byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    	byte[] keyBytes = this.secretKey.getBytes(StandardCharsets.UTF_8);
    	return Keys.hmacShaKeyFor(keyBytes);
    }
    
    public String generateToken(UserDetails userDetails) {
    	Map<String,Object> claims = new HashMap<>();
    	//eturn this.doGenerateToken(claims, userDetails.getUsername()); //working with this 
    	//as in jwt filter i am getting usermodel first using repo then passing email to load user by username
    	
    	//UserModel usermodel = (UserModel) userDetails; 
    	UserModel usermodel = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("invalid username"));
    	return this.doGenerateToken(claims, usermodel.getEmail());
    	//not working when setting email in subject.. actually need to set in below validate token code as well
    	//once set email here in subject and in below validate token.. then working fine
    }
    
    private String doGenerateToken(Map<String, Object> claims, String subject) {
    	return Jwts.builder()
    			.claims(claims)
    			.subject(subject)
    			.issuedAt(new Date(System.currentTimeMillis()))
    			.expiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
    			.signWith(getSigningKey())
    			.compact();
    }
    
    public Boolean isTokenExpired(String token) {
    	Date expiration = this.getExpirationDateFromToken(token);
    	return expiration.before(new Date());
    }
    
    public Boolean validateToken(String token, UserDetails userDetails) {
    	//if getting invalid token error.. this error is from here.. 
    	
    	UserModel usermodel = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(()->new UsernameNotFoundException("invalid username"));
    
    	String username = this.getUsernameFromToken(token);
    	
    	//return username.equals(userDetails.getUsername())&& (!(isTokenExpired(token)));//if not setting subject in email
    	return username.equals(usermodel.getEmail())&& (!(isTokenExpired(token)));//if setting email in subject
    }
    
}
