package com.onist.user.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// The LoginResponse class represents the data transfer object (DTO) for user login responses
// It contains the JWT access token, refresh token, user email, role, first name, last name, and a flag indicating if the user must change their password
// La classe LoginResponse représente l'objet de transfert de données (DTO) pour les réponses de connexion des utilisateurs
// Il contient le jeton d'accès JWT, le jeton de rafraîchissement, l'e-mail de l'utilisateur, le rôle, le prénom, le nom de famille et un indicateur indiquant si l'utilisateur doit changer son mot de passe
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
