package com.onist.user.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.onist.user.exception.InvalidRefreshTokenException;
import com.onist.user.exception.UserNotFoundException;
import com.onist.user.model.UserModel;
import com.onist.user.repository.UserRepository;
import com.onist.user.security.JwtTokenProvider;
import com.onist.user.service.dto.LoginRequest;
import com.onist.user.service.dto.LoginResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

// Centralizes all authentication logic: initial login and token renewal via refresh token.
 

    // Verifies the email/password via Spring Security, then generates the two tokens.
    // Login method to authenticate user and generate JWT tokens
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

    // Fetch user details from the database
    UserModel user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UserNotFoundException("User not found: " + request.getEmail()));

    // Generate JWT access and refresh tokens
        String accessToken = jwtTokenProvider.generateToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        return new LoginResponse(
            accessToken,
            refreshToken,
            user.getEmail(),
            user.getRole().name(),
            user.getFirstname(),
            user.getLastname()
        );   
    }

    // Validates the refresh token, retrieves the user, and issues new tokens.
    // Method to refresh JWT tokens using a valid refresh token
    public LoginResponse refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken) || !jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new InvalidRefreshTokenException("Invalid refresh token or expired: " + refreshToken);
        }

    // Extract email from the refresh token and fetch user details
        String email = jwtTokenProvider.getEmailFromToken(refreshToken);
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + email));
        
    // Generate new JWT access and refresh tokens            
        String newAccessToken = jwtTokenProvider.generateToken(user);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user);

        return new LoginResponse(
            newAccessToken,
            newRefreshToken,
            user.getEmail(),
            user.getRole().name(),
            user.getFirstname(),
            user.getLastname()
        );
    }   
}
