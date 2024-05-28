package com.example.BirthCertificate.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtill implements Serializable {

    //jjwt 0.9.0 , io.jsonwebtoken:: jaxb-api , javax.xml.bind 2.3.0  used with jjwt 0.9.0
    //jjwt-api, jjwt-impl, jjwt-jackson, io.jsonwebtoken, 0.11.5, 0.12.5
    //h2, com.h2database no version here

    private static final long serialVersionUID = 654352132132L;
    public static final long JWT_TOKEN_VALIDITY = 500*60*60;
    private final String secretKey = "randomKey";

    public String getUserNameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver){
        final Claims claims = getAllClaimFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimFromToken(String token){
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
        //return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public SecretKey getSigningKey(){
        byte[] keybyte = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keybyte);
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    public String doGenerateToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
                .signWith(getSigningKey())
                .compact();
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
    }

    public Boolean isTokenExpired(String token){
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        String username=getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !(isTokenExpired(token)));
    }
}
