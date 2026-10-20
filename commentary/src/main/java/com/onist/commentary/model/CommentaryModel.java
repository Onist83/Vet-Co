package com.onist.commentary.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// This class represents the data model for a commentary in the application
// Cette classe représente le modèle de données pour un commentaire dans l'application
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentaryModel {

    @Id
    private String id;

    private Long animalId;
    private String title;
    private String content;
    private String dateOfPublication;
}
