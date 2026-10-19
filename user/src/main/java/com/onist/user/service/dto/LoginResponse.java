package com.onist.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// The LoginResponse class represents the data transfer object (DTO) for user login responses
@Data
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private String refreshToken;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private boolean mustChangePassword;
}
