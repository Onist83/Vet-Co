package com.onist.user.service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotBlank(message="The refresh token is mandatory")
    private String refreshToken;
}
