package com.onist.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.onist.user.model.Role;

import lombok.Getter;
import lombok.Setter;

// This class is used to represent the request for creating a new user in the API
// It contains fields for email, password, first name, last name, and role, along with validation constraints to
// ensure that the required fields are provided and valid
// Cette classe sert à représenter la requête de création d'un nouvel utilisateur dans l'API
// Elle contient des champs pour l'adresse e-mail, le mot de passe, le prénom, le nom et le rôle,
// ainsi que des contraintes de validation garantissant que les champs requis sont fournis et valides.
@Getter
@Setter
public class CreateUserRequest {

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String password;

    @NotBlank(message = "Le prénom est obligatoire")
    private String firstname;

    @NotBlank(message = "Le nom est obligatoire")
    private String lastname;

    @NotNull(message = "Le rôle est obligatoire")
    private Role role;
}