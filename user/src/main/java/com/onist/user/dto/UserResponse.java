package com.onist.user.dto;

import com.onist.user.model.Role;

import lombok.Builder;
import lombok.Getter;

// This class is used to represent the response of a user entity in the API
// It contains fields for id, email, first name, last name, role, enabled status, and must change password status
// Cette classe sert à représenter la réponse concernant une entité utilisateur dans l'API
// Elle contient des champs pour l'identifiant, l'adresse e-mail, le prénom, le nom, le rôle, 
// le statut d'activation et l'indicateur de nécessité de changer de mot de passe
@Getter
@Builder
public class UserResponse {

    private Long id;
    private String email;
    private String firstname;
    private String lastname;
    private Role role;
    private boolean enabled;
    private boolean mustChangePassword;
}