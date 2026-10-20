package com.onist.user.service.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;

// The RefreshTokenRequest class represents the data transfer object (DTO) for refresh token requests
// It contains the refreshToken field, which is validated using Jakarta Bean Validation annotations to ensure it is not blank
// La classe RefreshTokenRequest représente l'objet de transfert de données (DTO) pour les demandes de rafraîchissement de jeton
// Elle contient le champ refreshToken, qui est validé à l'aide d'annotations Jakarta Bean Validation afin de garantir qu'il n'est pas vide
@Data
public class RefreshTokenRequest {
    @NotBlank(message = "Le refresh token est obligatoire")
    private String refreshToken;
}
