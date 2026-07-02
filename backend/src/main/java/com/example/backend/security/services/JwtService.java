package com.example.backend.security.services;

import java.security.Key;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private final Key key;  
    // 1 hr
    private Long accessExpTime = 1000L * 60 * 60 ;
    
    // 7 days
    private Long refreshExpTime = 1000L* 7 * 24 * 60 * 60;

    public JwtService(@Value("${jwt.secret}") String token){
        this.key = Keys.hmacShaKeyFor(token.getBytes());
    }

    public String generateAccessToken(String username,Collection<? extends GrantedAuthority> authorities ){
        String role  = authorities.stream().map(r->r.getAuthority()).findFirst().orElse("ROLE_USER");
        return Jwts.builder()
                    .setSubject(username)
                    .claim("role",role)
                    .claim("type", "accessToken")
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + accessExpTime))
                    .signWith(key)
                    .compact();
    }


    public boolean validToken(String token){
        try{
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
            return true;
        }catch(ExpiredJwtException e){
            throw e;
        }catch(JwtException e){
            throw e;
        }
    }

    public String getUsernameFromToken(String token){
        return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
    }

    public String generateRefreshToken(String username,Collection <?extends GrantedAuthority> authorities){
        String role = authorities.stream()
                                .map(r->r.getAuthority())
                                .findFirst()
                                .orElse("ROLE_USER");
        return Jwts.builder()
                    .setSubject(username)
                    .claim("role", role)
                    .claim("type", "refreshToken")
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + refreshExpTime))
                    .signWith(key)
                    .compact();
    }

    public boolean validRefreshToken(String token){
        try{
            Jws<Claims> claim = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

            String type = claim.getBody().get("type",String.class);
            return "refreshToken".equals(type);
        }catch(ExpiredJwtException e){
            throw e;
        }catch(JwtException e){
            throw e;
        }
    }
}
