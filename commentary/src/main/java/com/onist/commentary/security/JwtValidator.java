package com.onist.commentary.security;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

// This class is responsible for validating JWT tokens and extracting information from them
// Cette classe est responsable de la validation des jetons JWT et de l'extraction d'informations
@Component
public class JwtValidator {

    private final SecretKey secretKey;

    // Constructor that initializes the JwtValidator with a secret key for signing
    // and verifying JWT tokens
    // Constructeur qui initialise le JwtValidator avec une clé secrète pour signer
    // et vérifier les jetons JWT
    public JwtValidator(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    // Validates the provided JWT token by checking its signature and structure
    // Valide le jeton JWT fourni en vérifiant sa signature et sa structure
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Extracts the email (subject) from the provided JWT token
    // Extrait l'email (sujet) du jeton JWT fourni
    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    // Extracts the role from the provided JWT token
    // Extrait le rôle du jeton JWT fourni
    public String getRoleFromToken(String token) {
        return getClaims(token).get("role", String.class);
    }

    // Extracts the claims from the provided JWT token
    // Extrait les revendications du jeton JWT fourni
    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();

    }
}
