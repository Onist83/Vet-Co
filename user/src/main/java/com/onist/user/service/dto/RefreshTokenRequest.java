package com.onist.user.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// The RefreshTokenRequest class represents the data transfer object (DTO) for refresh token requests
@Data
public class RefreshTokenRequest {
    @NotBlank(message="Le refresh token est obligatoire")
    private String refreshToken;
}
