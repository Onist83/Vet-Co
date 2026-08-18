package com.onist.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    // The LoginResponse class represents the data transfer object (DTO) for user login responses.

    private String token;
    private String refreshToken;
    private String email;
    private String role;
    private String firstName;
    private String lastName;

}
