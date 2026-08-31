package com.onist.user.controller;

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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    // Authenticates a user and returns an access token and a refresh token
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));   
    }

    // Renews the access token using a valid refresh token.
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authService.refreshToken(request.getRefreshToken()));
    }

    // Mandatory password change upon first login (or voluntary change thereafter)
    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordRequest request) {
        authService.changePassword(request.getNewPassword());
        return ResponseEntity.ok().build();
    }
}
