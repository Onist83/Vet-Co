package com.onist.commentary.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onist.commentary.dto.CommentaryResponse;
import com.onist.commentary.dto.CreateCommentaryRequest;
import com.onist.commentary.service.CommentaryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping ("/api/v1/commentary")
@RequiredArgsConstructor 
public class CommentaryController {

    private final CommentaryService commentaryService;
    
    // Creates a new commentary
    @PostMapping("/create")
    public ResponseEntity<CommentaryResponse> createCommentary(
            @Valid @RequestBody CreateCommentaryRequest request) {

        CommentaryResponse created = commentaryService.createCommentary(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Retrieves all commentaries, ordered by publication date
    @GetMapping("/all")
    public ResponseEntity<List<CommentaryResponse>> findAll() {
        return ResponseEntity.ok(commentaryService.getAllOrderedByDate());
    }

    // Retrieves all commentaries for a given animal
    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<CommentaryResponse>> findByAnimalId(@PathVariable Long animalId) {
        return ResponseEntity.ok(commentaryService.findAllCommentaryByAnimalId(animalId));
    }

    // Retrieves a single commentary by its id
    @GetMapping("/{id}")
    public ResponseEntity<CommentaryResponse> findById(@PathVariable String id) {
        return ResponseEntity.ok(commentaryService.findCommentaryById(id));
    }

    // Updates an existing commentary
    @PutMapping("/update/{id}")
    public ResponseEntity<CommentaryResponse> updateCommentary(
            @PathVariable String id,
            @Valid @RequestBody CreateCommentaryRequest request) {

        CommentaryResponse updated = commentaryService.updateCommentary(id, request);
        return ResponseEntity.ok(updated);
    }

    // Deletes a single commentary by its id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCommentary(@PathVariable String id) {
        commentaryService.deleteCommentary(id);
        return ResponseEntity.noContent().build();
    }

    // Deletes all commentaries for a given animal — appelé par le service Animal via Feign lors de la suppression d'un animal
    @DeleteMapping("/animal/{animalId}")
    public ResponseEntity<Void> deleteAllByAnimalId(@PathVariable Long animalId) {
        commentaryService.deleteAllByAnimalId(animalId);
        return ResponseEntity.noContent().build();
    }
}
