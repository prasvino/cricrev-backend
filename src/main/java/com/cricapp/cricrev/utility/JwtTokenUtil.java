package com.cricapp.cricrev.utility;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.nimbusds.jwt.JWTClaimNames.EXPIRATION_TIME;
import java.security.Key;

@Component
@Service
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
   /* public  String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims,username);
    }*/

    public  String generateToken(String username) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        /*return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key)
                .compact();*/

        String jwt = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key, SignatureAlgorithm.HS256).compact(); // Specify the signing algorithm

        return jwt;
    }
    /*private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

    }*/
}