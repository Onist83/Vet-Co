package com.onist.commentary.dto;

import lombok.Builder;
import lombok.Getter;

@Getter 
@Builder 
public class CommentaryResponse {

    private String id;
    private Long animalId;
    private String title;
    private String content;
    private String dateOfPublication;
}
