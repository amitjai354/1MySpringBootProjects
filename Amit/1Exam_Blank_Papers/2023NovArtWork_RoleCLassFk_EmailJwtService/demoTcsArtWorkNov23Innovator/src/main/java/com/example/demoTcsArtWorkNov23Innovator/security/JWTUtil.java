package com.example.demoTcsArtWorkNov23Innovator.security;

import com.example.demoTcsArtWorkNov23Innovator.model.UserModel;
import com.example.demoTcsArtWorkNov23Innovator.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class JWTUtil implements Serializable {

    
    UserService userService;

    public static final long JWT_TOKEN_VALIDITY=500*60*60*1000;//5*60*60*1000
    String secretKey="randomkey123tooShortKeyE1234567898ABHGVVNgfdmhjn475877988";
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

    
}
