package com.onist.commentary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

// This class represents the request payload for creating a new commentary
// Cette classe représente la charge utile de la requête pour créer un nouveau commentaire
@Getter
@Setter
public class CreateCommentaryRequest {

    @NotNull(message = "L'identifiant de l'animal doit être renseigné")
    private Long animalId;

    @NotBlank(message = "Le titre du commentaire ne peut pas être vide")
    private String title;

    @NotBlank(message = "Le contenu du commentaire ne peut pas être vide")
    private String content;

    @NotBlank(message = "La date de publication doit être renseignée")
    private String dateOfPublication;
}
