package com.example.AmitSbTcsJwtV3Project.config;

import com.example.AmitSbTcsJwtV3Project.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class JwtUtil {
    // <!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt -->
		// <dependency>
		// 	<groupId>io.jsonwebtoken</groupId>
		// 	<artifactId>jjwt</artifactId>
		// 	<version>0.9.0</version>
		// </dependency>

		// <dependency>
		// 	<groupId>javax.xml.bind</groupId>
		// 	<artifactId>jaxb-api</artifactId>
		// 	<version>2.3.0</version>
		// </dependency>
        //eith above dependency working with 
        //String jwtSecret="amit"; as well
        //but woth new jjwt dependecy where impl jackson, not working with this
    String jwtSecret = "4261656C64756E67456G66879855555432H45562J2234";
    public static final long JWT_TOKEN_VALIDITY=5*60*60*1000;

//    public User getUser(final String token){
//        return null;
//        //get Username from Durghesh code then call findUser by user name from userRepo
//    }

    public String getUserNameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        //return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();//0.11.5 jwt Deprecated
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload(); //0.12.5 jwt
    }

    public Boolean isTokenExpired(String token){
        final Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //my new codes
    //private SecretKey getSigningKey() {
    //   return Jwts.SIG.HS256.key().build();//but here not using our String jwtsecret and works with 0.12.5 only
    //}
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);//works with both 0.11.5 and 0.12.5, usese our String jwtSecret
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                //.setClaims(claims)
                //.setSubject(userDetails.getUsername())
                //.setIssuedAt(new Date(System.currentTimeMillis()))
                //.setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*1000))
                //.signWith(SignatureAlgorithm.HS512,secret) //0.11.5 jwt Deprecated
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
                .signWith(this.getSigningKey())
                .compact();
        //now instead of signWith(Algo, key) , use signWith(Key)
        //Baeldung site:
        //Next, let’s add the jjwt-api, jjwt-impl and jjwt-jackson dependencies to the pom.xml:
        //<dependency>
        //    <groupId>io.jsonwebtoken</groupId>
        //    <artifactId>jjwt-api</artifactId>
        //    <version>0.12.5</version>
        //</dependency>
        //<dependency>
        //    <groupId>io.jsonwebtoken</groupId>
        //    <artifactId>jjwt-impl</artifactId>
        //    <version>0.12.5</version>
        //</dependency>
        //<dependency>
        //    <groupId>io.jsonwebtoken</groupId>
        //    <artifactId>jjwt-jackson</artifactId>
        //    <version>0.12.5</version>
        //</dependency>
        //convert a secret string to a Key instance to further encrypt it before using it to sign JWT.
        //First, let’s ensure the secret string is Base64 encoded:
        //private String jwtSecret = "4261656C64756E67";
        //Next, let’s create a Key object: SecretKey object is more secure so use this al;so used with verifyWith(SecretKey object)
        //4.2. Using SecretKey Instance
        //Also, we can form a strong secret key using the HMAC-SHA Algorithm to create a SecretKey instance.
        //Let’s create a SecretKey instance that returns a secret key:
        //SecretKey getSigningKey() {
        //    return Jwts.SIG.HS256.key().build();
        //}
        //Here, we directly use the HMAC-SHA algorithm without using a byte array. This forms a strong signature key.
        //Alternatively, we can create a SecretKey instance from a Base16 encoded string:
        //SecretKey getSigningKey() {
        //    byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        //    return Keys.hmacShaKeyFor(keyBytes);
        //}
        //This generates a strong SecretKey type to sign and verify JWT.
        //private Key getSigningKey() {
        //    byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
        //    return Keys.hmacShaKeyFor(keyBytes);
        //}
        //In the code above, we decode the jwtSecret into a byte array. Next, we invoke the hmacShaKeyFor() which accepts
        // keyBytes as a parameter on the Keys instance. This generates a secret key based on the HMAC algorithm.
        //In the case where the secret key is not Base64 encoded, we can invoke the getByte() method on the plain string:
        //private Key getSigningKey() {
        //    byte[] keyBytes = this.jwtSecret.getBytes(StandardCharsets.UTF_8);
        //    return Keys.hmacShaKeyFor(keyBytes);
        //}
        //However, this is not recommended because the secret may be poorly formed, and the string may contain non-UTF-8 characters.
        // Hence, we must ensure the key string is Base64 encoded before generating a secret key from it.
        //Notably, using the SecretKey instance over the Key instance is advisable because the new method named
        // verifyWith() to verify the token accepts the SecretKey type as a parameter.
        //4.3. Applying the Key
        //Now, let’s apply the secret key to sign the JWT of our application:
        //String generateJwtToken(Authentication authentication) {
        //    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        //    return Jwts.builder()
        //      .subject((userPrincipal.getUsername()))
        //      .issuedAt(new Date())
        //      .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
        //      .signWith(key)
        //      .compact();
        //}
        //The signWith() method takes the SecretKey instance as a parameter to append a unique signature to the token.

    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = this.getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}



// package com.example.demoTcsArtWorkNov23Innovator.security;

// import com.example.demoTcsArtWorkNov23Innovator.model.UserModel;
// import com.example.demoTcsArtWorkNov23Innovator.service.UserService;
// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import io.jsonwebtoken.io.Decoders;
// import io.jsonwebtoken.security.Keys;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Component;

// import javax.crypto.SecretKey;
// import java.io.Serializable;
// import java.nio.charset.StandardCharsets;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.Map;
// import java.util.function.Function;

// @Component
// public class JWTUtil implements Serializable {

//     @Autowired
//     UserService userService;

//     public static final long JWT_TOKEN_VALIDITY=500*60*60*1000;//5*60*60*1000
//     String secretKey="randomkey123tooShortKeyE1234567898ABHGVVNgfdmhjn475877988";
//     //String secretKey="andomkey1234261656C64756E67";//in 0.11.5 generated token but not in 0.12.5
//     //String secretKey="randomkey123";//working with this as well in 0.11.5 but in DoGenerate method need to comment sign with line completely
//     //working with this always even with parser builder just comment sign with in do generate
//     //if write signWith then sayoing 72 bit less than 256 bit in 0.11.5 also Durgesh also said that keep string large
//     //The JWT JWA Specification (RFC 7518, Section 3.2) states that keys used with HMAC-SHA algorithms
//     // MUST have a size >= 256 bits (the key size must be greater than or equal to the hash output size).
//     // Consider using the Jwts.SIG.HS256.key() builder (or HS384.key() or HS512.key()) to create a key
//     // guaranteed to be secure enough for your preferred HMAC-SHA algorithm.
//     //error when login api if secret key is too short.. it shoyld be 256 bit, but randomKey123 is 72 bit
//     //is there any security where this short one will work.. there is one 64 bit as well

//     private SecretKey getSigningKey() {
//         //byte[] keyBytes = Decoders.decode(secretKey);// this will not work, error Base64 is must
//         byte[] keyBytes = Decoders.BASE64.decode(secretKey);//works with both 0.11.5 and 0.12.5, usese our String jwtSecret
//         //byte[] keyBytes = this.secretKey.getBytes(StandardCharsets.UTF_8);//with this also key short error, if complete key then generating token
//         return Keys.hmacShaKeyFor(keyBytes);
//     }

//     public String getUsernameFromToken(String token){
//         return getClaimFromToken(token, Claims::getSubject);
//     }

//     public Date getExpirationDateFromToken(String token){
//         return getClaimFromToken(token, Claims::getExpiration);
//     }

//     public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver){
//         //We have 2 T in return Type, must remember that other wise error
//         final Claims claims = getAllClaimsFromToken(token);
//         return claimResolver.apply(claims);
//     }

//     private Claims getAllClaimsFromToken(String token){
//         //return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();//deprecated parser and signing key
//         //return Jwts.parser().parseClaimsJws(token).getBody();//parser is deprecated use parseBuilder instead say
//         //return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();//no deprecated here in 0.11.5 and generating token with long string
//         //parserBuilder not present in 0.12.5 here verifyWith only verifyWith not present in 0.11.5
//         return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();//verify with does not work with 0.12.5
//     }

//     private Boolean isTokenExpired(String token){
//         final Date expiration = this.getExpirationDateFromToken(token);
//         return expiration.before(new Date());
//     }

//     public String generateToken(UserDetails userDetails){
//         Map<String, Object> claims = new HashMap<>();
//         UserModel userModel = (UserModel) userDetails;
//         System.out.println(userModel.getEmail());//this is working perfectly,
//         //so we can create one method getEmail
//         //return doGenerateToken(claims, userDetails.getUsername());
//         return doGenerateToken(claims, userModel.getEmail());
//     }

//     private String doGenerateToken(Map<String, Object> claims, String subject){
//         return Jwts.builder() ///version 0.12.5
//                 .claims(claims)
//                 .subject(subject)
//                 .issuedAt(new Date(System.currentTimeMillis()))
//                 .expiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))//this is inside new date
//                 .signWith(getSigningKey()) // if comment this generating token but later when access any api with token then saying boit is small
//                 //now this is rule that string must be greater because in older jwt this same code used to work which we have wrtten in 0.11.5
//                 //but now this same code does not work for smaller string
//                 //.signWith(getSigningKey(), SignatureAlgorithm.HS256)//thi si deprecated in 0.12.5 but not in 0.11.5
//                 .compact();
// //        return Jwts.builder()
// //                .setClaims(claims)
// //                .setSubject(subject)
// //                .setIssuedAt(new Date(System.currentTimeMillis()))
// //                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
// //                //.signWith(SignatureAlgorithm.HS256, secretKey)//deperecated and same error also bit is less, if comment this line sign with
// //                //and compact without signWith then this is working with any length of string
// //                //.signWith(secretKey, SignatureAlgorithm.HS256) key, Algorithm we need to pass can use in 0.12.5 not deprecated
// //                //.signWith(getSigningKey(),SignatureAlgorithm.HS256)//this is not dep[recated, can tell Base 64 encoded key to which algop use to encrypt
// //                //.signWith(getSigningKey(),SignatureAlgorithm.ES384)//this is not dep[recated, can tell Base 64 encoded key to which algop use to encrypt
// //                //.signWith(getSigningKey(),SignatureAlgorithm.RS256)//same error
// //                .compact();
//     }

//     public Boolean validateToken(String token, UserDetails userDetails){
//         final String username = this.getUsernameFromToken(token);
//         UserModel userModel = (UserModel) userDetails;
//         //return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
//         return username.equals(userModel.getEmail()) && !isTokenExpired(token);
//     }
// }


