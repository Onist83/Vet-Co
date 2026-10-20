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

// The JwtTokenProvider class is responsible for generating and validating JSON Web Tokens (JWTs)
// for user authentication and authorization in the application. It uses a secret key to sign the tokens and
// provides methods to generate access and refresh tokens, validate tokens, and extract information from the tokens
// La classe JwtTokenProvider est chargée de générer et de valider des jetons JSON Web Tokens (JWT)
// pour l'authentification et l'autorisation des utilisateurs au sein de l'application 
// Elle utilise une clé secrète pour signer les jetons et propose des méthodes permettant de générer des jetons d'accès
// et de rafraîchissement, de valider les jetons, ainsi que d'en extraire des informations
@Component
public class JwtTokenProvider {

    // Key injected via application.yaml, never randomly generated
    // Clé injectée via application.yaml, jamais générée aléatoirement
    private final SecretKey secretKey;
    private final long accessTokenExpirationTime;
    private final long refreshTokenExpirationTime;

    // Constructor to initialize the JwtTokenProvider with the secret key and
    // expiration times for access and refresh tokens
    // Constructeur pour initialiser le JwtTokenProvider avec la clé secrète et les
    // temps d'expiration pour les jetons d'accès et de rafraîchissement
    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long accessTokenExpirationTime,
            @Value("${jwt.refresh-expiration}") long refreshTokenExpirationTime) {

        // Decode the Base64-encoded secret key and create a SecretKey instance
        // Décoder la clé secrète encodée en Base64 et créer une instance de SecretKey
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.accessTokenExpirationTime = accessTokenExpirationTime;
        this.refreshTokenExpirationTime = refreshTokenExpirationTime;
    }

    // Generates the access token used to authenticate each request
    // Génère le jeton d'accès utilisé pour authentifier chaque requête
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

    // Generates the refresh token used to obtain a new access token when the
    // current one expires
    // Génère le jeton de rafraîchissement utilisé pour obtenir un nouveau jeton
    // d'accès lorsque celui en cours expire
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
    // Valide le jeton en vérifiant sa signature et sa date d'expiration
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Checks if the token is a refresh token by examining the "type" claim in the
    // token's payload
    // Vérifie si le jeton est un jeton de rafraîchissement en examinant la
    // revendication "type" dans la charge utile du jeton
    public boolean isRefreshToken(String token) {
        Claims claims = getClaims(token);
        return "refresh".equals(claims.get("type", String.class));
    }

    // Extracts the email from the token's payload by retrieving the subject claim
    // Extrait l'email de la charge utile du jeton en récupérant la revendication
    // subject
    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }

    // Extracts the role from the token's payload by retrieving the "role" claim
    // Extrait le rôle de la charge utile du jeton en récupérant la revendication
    // "role"
    public String getRoleFromToken(String token) {
        return getClaims(token).get("role", String.class);
    }

    // Extracts the user ID from the token's payload by retrieving the "userId"
    // claim
    // Extrait l'ID utilisateur de la charge utile du jeton en récupérant la
    // revendication "userId"
    public Long getUserIdFromToken(String token) {
        return getClaims(token).get("userId", Long.class);
    }

    // Retrieves the claims from the token's payload by parsing the token and
    // returning the payload as a Claims object
    // Récupère les revendications de la charge utile du jeton en analysant le jeton
    // et en renvoyant la charge utile sous forme d'objet Claims
    private Claims getClaims(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }
}
