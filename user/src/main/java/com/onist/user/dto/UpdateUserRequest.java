package com.onist.user.dto;

import com.onist.user.model.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

// This class is used to represent the request for updating a user entity in the API 
// It contains fields for email, first name, last name, and role, along with validation constraints to ensure that the required fields are provided and valid
@Getter
@Setter
public class UpdateUserRequest {

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le prénom est obligatoire")
    private String firstname;

    @NotBlank(message = "Le nom est obligatoire")
    private String lastname;

    @NotNull(message = "Le rôle est obligatoire")
    private Role role;
}