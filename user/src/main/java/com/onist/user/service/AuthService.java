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
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final UserService userService;

// Centralizes all authentication logic: initial login and token renewal via refresh token
 

    // Verifies the email/password via Spring Security, then generates the two tokens
    // Login method to authenticate user and generate JWT tokens
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

    // Fetch user details from the database
    UserModel user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new UserNotFoundException("L'utilisateur avec l'email: " + request.getEmail() + " n'as pas été trouvé"));

    // Generate JWT access and refresh tokens
        String accessToken = jwtTokenProvider.generateToken(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(user);

        return new LoginResponse(
            accessToken,
            refreshToken,
            user.getEmail(),
            user.getRole().name(),
            user.getFirstname(),
            user.getLastname(),
            user.isMustChangePassword()
        );   
    }

    // Validates the refresh token, retrieves the user, and issues new tokens
    // Method to refresh JWT tokens using a valid refresh token
    public LoginResponse refreshToken(String refreshToken) {
        if (!jwtTokenProvider.validateToken(refreshToken) || !jwtTokenProvider.isRefreshToken(refreshToken)) {
            throw new InvalidRefreshTokenException("Token: "+ refreshToken + " invalide ou expiré");
        }

    // Extract email from the refresh token and fetch user details
        String email = jwtTokenProvider.getEmailFromToken(refreshToken);
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("L'utilisateur avec l'email: " + email + " n'as pas été trouvé"));

        if (!user.isEnabled()) {
            throw new InvalidRefreshTokenException("L'email: " + email + " est désactivé");
        }
        
    // Generate new JWT access and refresh tokens            
        String newAccessToken = jwtTokenProvider.generateToken(user);
        String newRefreshToken = jwtTokenProvider.generateRefreshToken(user);

        return new LoginResponse(
            newAccessToken,
            newRefreshToken,
            user.getEmail(),
            user.getRole().name(),
            user.getFirstname(),
            user.getLastname(),
            user.isMustChangePassword()
        );
    } 

    // Change the logged-in user's password
    public void changePassword(String newPassword) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.changePassword(email, newPassword);
    }  
}
