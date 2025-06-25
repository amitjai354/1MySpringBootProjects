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

@Component //required here as we will autowire this class in other classes
public class JwtService {
	
	//next time in exam, if not working with Tcs code, delete all tcs code if required delete class also
	//write your own code.. you have question and you just have to run that
	
	
	//@Value("${jwt.secret}")
	//private String JWT_SECRET;
	
	//write secret given in exam
	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
	
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
		
		
		//return Jwts.parserBuilder().setSigningKey(this.getSignKey()).build().parseClaimsJws(token).getBody();
		//return Jwts.parser().verifyWith(this.getSignKey()).build().parseSignedClaims(token).getPayload(); //in 0.12.6 must need SecretKey not Key
		//return Jwts.parser().setSigningKey(this.getSignKey()).build().parseClaimsJws(token).getBody();//in 0.12.6 setSigningKey, parseClaimsJws, getBody are deprecated
		//return Jwts.parser().setSigningKey(this.getSignKey()).build().parseSignedClaims(token).getPayload();//in 0.12.6 getSigningKey is deprecated here
		//return Jwts.parser().setSigningKey(this.getSignKey()).parseClaimsJws(token).getBody();//in 0.11.5 parser, setsigningKey deprecated
		
		return Jwts.parserBuilder().setSigningKey(this.getSignKey()).build().parseClaimsJws(token).getBody();
		//in 0.11.5 we do not have verify with, get Payload, nothing deprecated here, 
		//works with Key, Secret key both
		
		//parseClaimsJwt karne par UnSigned JWT not supportedException : login api me error nhi aaya.. error aaya when we run main apis, 
		//as we are using SecurityContextHolder there, 
		//debug karne par code main controller ya service me nhi gya.. directly gave Unchecked Exception means runtime excpetion
		//waha ek attribute tha t, uss par hover kiya to exception bata diya
		//waise test case log me bhi bata diya tha ye directly, no need to debug, as it was runtime exception
		
		//yaha to security code ke baad, login , signUp run kar rha tha, issue api ke code baad aaya..
		//but jab api code dekha to waha aisa kuch tha nhi jo unsupported Jwt error de.. 
		//JWT exception means, jwtservice me kahi issue hai.. 
		//so please check all code once, any misTyping issue or package not imported properly
		
	}
	
	private Boolean isTokenExpired(String token) {
		Date expiration = this.extractExpiration(token);
		return expiration.before(new Date());
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		String username = this.extractUsername(token);
		//return (username.equals(userDetails.getUsername()) && (!this.isTokenExpired(token)));
		return username.equals(userDetails.getUsername()) && (!this.isTokenExpired(token));
	}
	
	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		return this.createToken(claims, username);
	}
	
	private String createToken(Map<String, Object> claims, String username) {
		//all test cases passing with this
		//.signWith(SignatureAlgorithm.HS256, getSignKey())//deprecated
		//this was given in this exam..  
		
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
				.signWith(getSignKey(), SignatureAlgorithm.HS256) //works in 0.11.5
				//.signWith(getSignKey(), SignatureAlgorithm.HS256) //deprecated in jwt 0.12.6
				//.signWith(SignatureAlgorithm.HS256, getSignKey()) //0.12.6
				//.signWith(getSignKey()) //not deprecated
				//.signWith(getSignKey(), Jwts.SIG.HS256) //this one is not deprecated in 0.12.6 jwt
				.compact();
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
		//Be carefull we have Decoders and Decode both in io.json.webtoken
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
