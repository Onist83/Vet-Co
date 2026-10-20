package com.onist.user.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.onist.user.exception.InvalidRefreshTokenException;
import com.onist.user.exception.UserNotFoundException;
import com.onist.user.model.UserModel;
import com.onist.user.repository.UserRepository;
import com.onist.user.security.JwtTokenProvider;
import com.onist.user.service.dto.LoginRequest;
import com.onist.user.service.dto.LoginResponse;

import lombok.RequiredArgsConstructor;

// The AuthService class handles user authentication, including login and token refresh operations
// It uses Spring Security's AuthenticationManager to authenticate users and generates JWT tokens for authenticated users
// La classe AuthService gère l'authentification des utilisateurs, y compris les opérations de connexion et de rafraîchissement des jetons
// Elle utilise l'AuthenticationManager de Spring Security pour authentifier les utilisateurs et génère des jetons JWT pour les utilisateurs authentifiés
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;

    // Centralizes all authentication logic: initial login and token renewal via
    // refresh token
    // Centralise toute la logique d'authentification : connexion initiale et
    // renouvellement du token via le refresh token

    // Verifies the email/password via Spring Security, then generates the two
    // tokens
    // Login method to authenticate user and generate JWT tokens
    // Vérifie l'email/mot de passe via Spring Security, puis génère les deux tokens
    // Méthode de connexion pour authentifier l'utilisateur et générer les jetons
    // JWT
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // Fetch user details from the database
        // Récupère les informations de l'utilisateur depuis la base de données
        UserModel user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(
                        "L'utilisateur avec l'email: " + request.getEmail() + " n'as pas été trouvé"));
        UserModel user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException(
                        "L'utilisateur avec l'email: " + request.getEmail() + " n'as pas été trouvé"));

        // Generate JWT access and refresh tokens
        // Génère les jetons JWT d'accès et de rafraîchissement
        String accessToken = jwtTokenProvider.generateToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        return new LoginResponse(
                accessToken,
                refreshToken,
                user.getEmail(),
                user.getRole().name(),
                user.getFirstname(),
                user.getLastname(),
                user.isMustChangePassword());
    }

    // Validates the refresh token, retrieves the user, and issues new tokens
    // Valide le refresh token, récupère l'utilisateur et émet de nouveaux jetons
    public LoginResponse refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken) || !jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new InvalidRefreshTokenException("Token: " + refreshToken + " invalide ou expiré");
        }

        // Extract email from the refresh token and fetch user details
        // Extrait l'email du refresh token et récupère les informations de
        // l'utilisateur
        String email = jwtTokenProvider.getEmailFromToken(refreshToken);
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(
                        "L'utilisateur avec l'email: " + email + " n'as pas été trouvé"));

        if (!user.isEnabled()) {
            throw new InvalidRefreshTokenException("L'email: " + email + " est désactivé");
        }

        // Generate new JWT access and refresh tokens
        // Génère de nouveaux jetons JWT d'accès et de rafraîchissement
        String newAccessToken = jwtTokenProvider.generateToken(user);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user);

        return new LoginResponse(
                newAccessToken,
                newRefreshToken,
                user.getEmail(),
                user.getRole().name(),
                user.getFirstname(),
                user.getLastname(),
                user.isMustChangePassword());
    }

    // Change the logged-in user's password
    // Change le mot de passe de l'utilisateur connecté
    public void changePassword(String newPassword) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.changePassword(email, newPassword);
    }
}
