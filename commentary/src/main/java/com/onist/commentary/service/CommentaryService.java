package com.onist.commentary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onist.commentary.dto.CommentaryResponse;
import com.onist.commentary.dto.CreateCommentaryRequest;
import com.onist.commentary.exception.CommentaryNotFoundException;
import com.onist.commentary.model.CommentaryModel;
import com.onist.commentary.repository.CommentaryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentaryService {
    private final CommentaryRepository commentaryRepository;

    //
    private CommentaryResponse toCommentaryResponse(CommentaryModel commentary) {
        return CommentaryResponse.builder()
                .id(commentary.getId())
                .animalId(commentary.getAnimalId())
                .title(commentary.getTitle())
                .content(commentary.getContent())
                .dateOfPublication(commentary.getDateOfPublication())
                .build();
    }

    //
     private CommentaryModel getCommentaryOrThrow(String id) {
        return commentaryRepository.findById(id)
                .orElseThrow(() -> new CommentaryNotFoundException("Le commentaire avec l'ID " + id + " n'a pas été trouvé."));
    }

    //
    public CommentaryResponse createCommentary(CreateCommentaryRequest request) {
    
        CommentaryModel commentary = CommentaryModel.builder()
                .animalId(request.getAnimalId())
                .title(request.getTitle())
                .content(request.getContent())
                .dateOfPublication(request.getDateOfPublication())
                .build();
        
        CommentaryModel savedCommentary = commentaryRepository.save(commentary);
        return toCommentaryResponse(savedCommentary);         

    }

     
    // Retrieves all commentaries, ordered by date of publication
    public List<CommentaryResponse> getAllOrderedByDate() {
        return commentaryRepository.findAllByOrderByDateOfPublicationAsc()
                .stream()
                .map(this::toCommentaryResponse)
                .toList();
    }

    // Retrieves all commentaries for a given animal
    public List<CommentaryResponse> findAllCommentaryByAnimalId(Long animalId) {
        return commentaryRepository.findByAnimalId(animalId)
                .stream()
                .map(this::toCommentaryResponse)
                .toList();
    }

    // Retrieves a single commentary by its id. Throws CommentaryNotFoundException if not found
    public CommentaryResponse findCommentaryById(String id) {
        return toCommentaryResponse(getCommentaryOrThrow(id));
    }

    // Updates an existing commentary
    public CommentaryResponse updateCommentary(String id, CreateCommentaryRequest request) {
        CommentaryModel existingCommentary = getCommentaryOrThrow(id);

        existingCommentary.setTitle(request.getTitle());
        existingCommentary.setContent(request.getContent());
        existingCommentary.setDateOfPublication(request.getDateOfPublication());

        CommentaryModel updatedCommentary = commentaryRepository.save(existingCommentary);
        return toCommentaryResponse(updatedCommentary);
    }

    // Deletes a single commentary by its id
    public void deleteCommentary(String id) {
        if (!commentaryRepository.existsById(id)) {
            throw new CommentaryNotFoundException("Le commentaire avec l'ID " + id + " n'a pas été trouvé.");
        }
        commentaryRepository.deleteById(id);
    }

    // Deletes all commentaries for a given animal 
    // Called by the Animal service when an animal is deleted
    public void deleteAllByAnimalId(Long animalId) {
        List<CommentaryModel> commentaries = commentaryRepository.findByAnimalId(animalId);
        commentaryRepository.deleteAll(commentaries);
    }
}