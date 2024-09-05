package com.example.ArtNew.securityConfiguration;

import com.example.ArtNew.model.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//15min

@Component
public class JwtUtill {

    final long JWT_TOKEN_VALIDITY = 5*60*60*1000;
    final String mySecret = "randome123E576ghjlhkWRETFERV6846323rbhdclhsdhckdy34787843hd";

    public String getUsernameFromToken(String token){
        return this.getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token){
        return this.getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver){
        final Claims claims = this.getAllClaimFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimFromToken(String token){
        return Jwts.parser().setSigningKey(mySecret).parseClaimsJws(token).getBody();
        //return Jwts.parser().verifyWith(getSigningKey()).parseSignedClaims(token).getPayload();
    }

//    public SecretKey getSigningKey(){
//        byte[] keyBytes = Decoders.Base64.decode(mySecret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

    public Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(mySecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims= new HashMap<>();
        UserModel userModel = (UserModel) userDetails;
        return this.doGenerateToken(claims, userModel.getEmail());
    }

    public String doGenerateToken(Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY))
                //.signWith(SignatureAlgorithm.HS256, mySecret)
                .signWith(getSigningKey())
                .compact();
    }

    public Boolean isTokenExpired(String token){
        Date expiration = this.getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        String username = this.getUsernameFromToken(token);
        UserModel userModel = (UserModel) userDetails;
        return username.equals(userModel.getEmail()) && !isTokenExpired(token);
    }

}
