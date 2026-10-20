package com.onist.user.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.Data;

// The ChangePasswordRequest class represents the data transfer object (DTO) for change password requests
// It contains the newPassword field, which is validated using Jakarta Bean Validation annotations to ensure it meets the required criteria for a secure password
// La classe ChangePasswordRequest représente l'objet de transfert de données (DTO) pour les demandes de changement de mot de passe
// Elle contient le champ newPassword, qui est validé à l'aide d'annotations Jakarta Bean Validation afin de garantir qu'il répond aux critères requis pour un mot de passe sécurisé
@Data
public class ChangePasswordRequest {
    @NotBlank(message = "Le nouveau mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9]).*$", message = "Le mot de passe doit contenir au moins une majuscule, un chiffre et un caractère spécial")
    private String newPassword;
}
