package com.onist.commentary.model;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

