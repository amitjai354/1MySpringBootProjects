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
	
	
	
	//java.security.Key  or  java.awt.RenderingHints.Key;
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
		
		
/*------------------------------------------------------------------------------------------------------
 --=========how to set alg in jwt header in jwt token: same question asked on GIT========---------
 The alg header is set automatically by JJWT based on the signature algorithm (for signed JWTs, aka JWSs) 
 or key management algorithm (for encrypted JWTs, aka JWEs) specified during JWT building. For example, for JWSs:

Jwts.builder()
    .signWIth(key) // JJWT implicitly chooses the appropriate algorithm based on the key type and size, or:
   //.signWith(key, aSecureDigestAlgorithm) // you explicitly set the algorithm, usually via a Jwts.SIG.**** constant
  ...
 
 */
 
//---------------------------------------------------------------------
// --=========How to set JWT type in JWT Header as of 0.12.6 : stack over flow question==
//Note that the JWT specifications suggest that you do not need set the JWT typ (type) header when you are certain what you're parsing is a JWT,
		
		//Header from io.jsonWebToken
//		Header header = Jwts.header(); //type mismatch error so can not use
//		header.setType("JWT");
		
		//this is deprecated in jwt 0.12.6 means after 0.12 jwt but working 0.11.5
		Map<String, Object> headerMap = new HashMap<>();
		headerMap.put("typ", "JWT" ); //key is String and value is Object so it can be String or anything, in exam both value is STRINg so passing as string
		headerMap.put("alg", SignatureAlgorithm.HS256.getValue()); //.getValue return string as in exa, given alg as string: "alg": "HS256"
//{
//  "alg": "HS256",
//  "typ": "JWT"
//}


		
		return Jwts.builder()
		
				//.setHeader(headerMap)//deprecated in jwt 0.12.5 , no need of and() with this
				//.setHeaderParam("typ", "JWT")//deprecated in jwt 0.12.5
				//.header().type("JWT").setAlgorithm(SignatureAlgorithm.HS256.getValue()) //setAlgorithm is deprecated in jwt 0.12.5
				//.header().type("JWT").add("alg", SignatureAlgorithm.HS256.getValue())//deprecated Signature algorithm in 0.12 jwt
				
				//.header().type("JWT").add("alg", Jwts.SIG.HS256) //Unsupported value type. Expected: java.lang.String, found: io.jsonwebtoken.impl.security.DefaultMacAlgorithm
				//.header().type("JWT").add("alg", Jwts.SIG.HS256)//this is working but does not seems right
				
				//.header().type("JWT") //this working in 0.12.6 but present in 0.11.5, alg in header is defined by signWith(SecretKey, alg), no need to set separately in 0.12.6
				//.and()//nothing for algo that we can use after and(), this is required with this header() only in 0.12.6
				
				//above one is header part
				
				
				//.issuer(null)//if asks for any issuer
		
				//.header().type("JWT").and() //in 0.12.6 in header part
				.setHeader(headerMap) //in 0.11.5 header part
				.setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
				//from claims till here is Payload part
				
				//below is mainly Signature part
				//.signWith(getSignKey(), Jwts.SIG.HS256) //works in 0.12.6 jwt but not present in 0.11.5, SignatureAlgorith is deprecated in 0.12.6
				.signWith(getSignKey(), SignatureAlgorithm.HS256) //works in 0.11.5
				
				//.signWith(getSignKey(), SignatureAlgorithm.HS256) //deprecated in jwt 0.12.6
				//.signWith(SignatureAlgorithm.HS256, getSignKey()) //0.12.6
				//.signWith(getSignKey()) //not deprecated
				//.signWith(getSignKey(), Jwts.SIG.HS256) //this one is not deprecated in 0.12.6 jwt but not present in 0.11.5
				
				//The signWith() method takes the SecretKey instance as a parameter to append a unique signature to the token.
				//this is mainly Signature part
				
				.compact();
				
				
/*

//it was given in the exam that String should be Base64 decode HS256 encrpted: below is exam requirement:
//ensure that claims in key should be as follows
//HEADER{ "alg":"HS256", "typ":"JWT" }
//PAYLOAD{ "sub":"Dev", (username)
// "iat":1576071104408, (time of creation in millisecond)
// "exp":1576072904408, (expiry time in millisecond, each token is valid for 30 min)}
//SIGNATURE should be signed with secret key encoded in base64


In its compact form, JSON Web Tokens consist of three parts separated by dots (.), which are:
Header.Payload.Signature
Therefore, a JWT typically looks like the following: xxxxx.yyyyy.zzzzz

Header:
The header typically consists of two parts: the type of the token, which is JWT, and the signing algorithm being used, such as HMAC SHA256 or RSA.
For example:
{
  "alg": "HS256",
  "typ": "JWT"
}
Then, this JSON is Base64Url encoded to form the first part of the JWT.	 and forms xxxxx. part: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9

Payload:
The payload contains the information about the user also called as a claim and some additional information 
including the timestamp at which it was issued and the expiry time of the token.
{
    "userId": 123,
    "role": "admin",
    "exp": 1672531199
}
Common claim types:
iss (Issuer): Identifies who issued the token.  eg ADMIN issues to USER  for login
sub (Subject): Represents the user or entity the token is about.
aud (Audience): Specifies the intended recipient.  eg ADMIN issues to USER for login
exp (Expiration): Defines when the token expires.
iat (Issued At): Timestamp when the token was created.
nbf (Not Before): Specifies when the token becomes valid.
Base64Url Encoded Payload : eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNzA4MzQ1MTIzLCJleHAiOjE3MDgzNTUxMjN9

Signature:
The signature ensures token integrity and is generated using the header, payload, and a secret key. In this example we will use HS256 algorithm to implement the Signature part
HMACSHA256(
    base64UrlEncode(header) + "." + base64UrlEncode(payload),
    secret
)
Example Signature: SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c

The signWith() method takes the SecretKey instance as a parameter to append a unique signature to the token.
*/
	}
	
	
	
	/* version 0.12.6 
	
	.header().type("JWT").and()
	.claims(claims)
	.subject(username)
	.issuedAt(new Date(System.currentTimeMillis()))
	.expiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
	.signWith(getSignKey(), Jwts.SIG.HS256)
	.compact();
	
	*/
	
	

}
