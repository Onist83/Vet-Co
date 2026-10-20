package com.onist.commentary.dto;

import lombok.Builder;
import lombok.Getter;

// This class represents the response object for a commentary
// Cette classe représente l'objet de réponse pour un commentaire
@Getter
@Builder
public class CommentaryResponse {

    private String id;
    private Long animalId;
    private String title;
    private String content;
    private String dateOfPublication;
}
