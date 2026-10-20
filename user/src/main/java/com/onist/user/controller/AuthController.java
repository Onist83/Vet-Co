package com.onist.user.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onist.user.service.AuthService;
import com.onist.user.service.dto.ChangePasswordRequest;
import com.onist.user.service.dto.LoginRequest;
import com.onist.user.service.dto.LoginResponse;
import com.onist.user.service.dto.RefreshTokenRequest;

import lombok.RequiredArgsConstructor;

// The AuthController class is responsible for handling authentication-related requests, such as user login, token refresh, and password change
// It defines endpoints for these operations and delegates the actual processing to the AuthService
// La classe AuthController est responsable de la gestion des requêtes liées à l'authentification, telles que la connexion des utilisateurs, le rafraîchissement des jetons et le changement de mot de passe
// Elle définit des points de terminaison pour ces opérations et délègue le traitement réel à l'AuthService
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // Authenticates a user and returns an access token and a refresh token
    // Authentifie un utilisateur et renvoie un jeton d'accès et un jeton de
    // rafraîchissement
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    // Renews the access token using a valid refresh token
    // Renouvelle le jeton d'accès en utilisant un jeton de rafraîchissement valide
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
    }

    // Mandatory password change upon first login (or voluntary change thereafter)
    // Changement de mot de passe obligatoire lors de la première connexion (ou
    // changement volontaire par la suite)
    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(request.getNewPassword());
        return ResponseEntity.ok().build();
    }
}
