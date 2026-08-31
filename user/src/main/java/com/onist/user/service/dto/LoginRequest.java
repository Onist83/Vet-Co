package com.onist.user.service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    // The LoginRequest class represents the data transfer object (DTO) for user login requests
    // It contains the email and password fields, which are validated using Jakarta Bean Validation annotations

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email est non valide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;
}
