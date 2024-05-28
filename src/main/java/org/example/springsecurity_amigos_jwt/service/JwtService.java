package org.example.springsecurity_amigos_jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "BD294E35A47C53A92E22D53D7D254782C92FF97338F54BFE652A38FA72";

    public String extractUserName(String jwtToken){
        return extractOneClaim(jwtToken , Claims::getSubject); //subject is the username or email of user
    }

    public <T> T extractOneClaim(String jwtToken , Function<Claims , T> claimGenerator){
        Claims claims = extractAllClaims(jwtToken);
        return claimGenerator.apply(claims);
    }

    public Claims extractAllClaims(String jwtToken){
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateJwtTokenWithExtraClaims(Map<String , Object> extraClaim , UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extraClaim)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSigningKey() , SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJwtTokenWithoutExtraClaims(UserDetails userDetails) {
        return generateJwtTokenWithExtraClaims(new HashMap<>() , userDetails);
    }

    public boolean isTokenValid(String token , UserDetails userDetails ){
        String username = extractUserName(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractOneClaim(token , Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }

}





