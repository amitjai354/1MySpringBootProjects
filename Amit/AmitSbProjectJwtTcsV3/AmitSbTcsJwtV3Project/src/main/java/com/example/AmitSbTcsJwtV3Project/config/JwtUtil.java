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
