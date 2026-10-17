
package com.klu.microservices.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.klu.microservices.dto.TokenResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET =
        "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=";

    private final SecretKey key;;

    public JwtService() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Generate a JWT token
    public String generateToken(String username,
                                List<String> roles) {

        return Jwts.builder()
                .subject(username)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(new Date(
                    System.currentTimeMillis() + 600000 ))
                .signWith(key)
                .compact();
    }

    // Validate JWT and extract claims
    public TokenResponse validateToken(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String username = claims.getSubject();

        List<?> rawRoles = claims.get("roles", List.class);
        List<String> roles = new ArrayList<>();

        if (rawRoles != null) {
            for (Object role : rawRoles) {
                if (!(role instanceof String)) {
                    throw new JwtException("Invalid roles claim");
                }
                roles.add((String) role);
            }
        }

        return new TokenResponse(
                "Token Valid", username, roles);
    }
}
