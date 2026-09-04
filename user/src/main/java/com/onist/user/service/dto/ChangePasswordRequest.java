package com.onist.user.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

// The ChangePasswordRequest class represents the data transfer object (DTO) for change password requests
@Data
public class ChangePasswordRequest {
    @NotBlank(message = "Le nouveau mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
     @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).*$",
        message = "Le mot de passe doit contenir au moins une majuscule, un chiffre et un caractère spécial"
    )
    private String newPassword;
}
