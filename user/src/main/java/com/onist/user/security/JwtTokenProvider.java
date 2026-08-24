package com.onist.user.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.onist.user.model.UserModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

    //Key injected via application.yaml, never randomly generated
    private final SecretKey secretKey;
    private final long accessTokenExpirationTime;
    private final long refreshTokenExpirationTime;

    // Constructor to initialize the JwtTokenProvider with the secret key and expiration times for access and refresh tokens
    public JwtTokenProvider(
        @Value("${jwt.secret}") String secret,
        @Value("${jwt.expiration}") long accessTokenExpirationTime,
        @Value("${jwt.refresh-expiration}") long refreshTokenExpirationTime) {

        // Decode the Base64-encoded secret key and create a SecretKey instance        
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenExpirationTime = accessTokenExpirationTime;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }

    // Generates the access token used to authenticate each request
    public String generateToken(UserModel user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .claim("userId", user.getId())
                .claim("type", "access")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpirationTime))
                .signWith(secretKey)
                .compact();
    }

    // Generates the refresh token used to obtain a new access token when the current one expires
    public String generateRefreshToken(UserModel user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("userId", user.getId())
                .claim("type", "refresh")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationTime))
                .signWith(secretKey)
                .compact();
    }


    // Validates the token by checking its signature and expiration date
    public boolean validateToken(String token) {
    try {
        Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
        return true;
    } catch (JwtException e) {
        return false;
    }
}

    // Checks if the token is a refresh token by examining the "type" claim in the token's payload
    public boolean isRefreshToken(String token) {
        Claims claims = getClaims(token);
        return "refresh".equals(claims.get("type", String.class));
    }

    // Extracts the email from the token's payload by retrieving the subject claim
    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();   
    }

    // Extracts the role from the token's payload by retrieving the "role" claim
    public String getRoleFromToken(String token) {
        return getClaims(token).get("role", String.class);
    }

    // Extracts the user ID from the token's payload by retrieving the "userId" claim
    public Long getUserIdFromToken(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    // Retrieves the claims from the token's payload by parsing the token and returning the payload as a Claims object
    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }
}
