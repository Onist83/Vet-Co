package com.onist.commentary.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.onist.commentary.dto.CommentaryResponse;
import com.onist.commentary.dto.CreateCommentaryRequest;
import com.onist.commentary.exception.CommentaryNotFoundException;
import com.onist.commentary.model.CommentaryModel;
import com.onist.commentary.repository.CommentaryRepository;

import lombok.RequiredArgsConstructor;

// This service class contains the business logic for managing commentaries in the application
// It interacts with the CommentaryRepository to perform CRUD operations and convert between the data model and DTO 
// Cette classe de service contient la logique métier pour la gestion des commentaires dans l'application
// Elle interagit avec le `CommentaryRepository` pour effectuer des opérations CRUD et convertir entre le modèle de données et le DTO
@Service
@RequiredArgsConstructor
public class CommentaryService {
    private final CommentaryRepository commentaryRepository;

    // Converts a CommentaryModel to a CommentaryResponse DTO
    // Convertit un `CommentaryModel` en un DTO `CommentaryResponse`
    private CommentaryResponse toCommentaryResponse(CommentaryModel commentary) {
        return CommentaryResponse.builder()
                .id(commentary.getId())
                .animalId(commentary.getAnimalId())
                .title(commentary.getTitle())
                .content(commentary.getContent())
                .dateOfPublication(commentary.getDateOfPublication())
                .build();
    }

    // Retrieves a CommentaryModel by its id or throws CommentaryNotFoundException
    // if not found
    // Récupère un `CommentaryModel` par son identifiant ou lance une
    // `CommentaryNotFoundException` s'il n'est pas trouvé
    private CommentaryModel getCommentaryOrThrow(String id) {
        return commentaryRepository.findById(id)
                .orElseThrow(() -> new CommentaryNotFoundException(
                        "Le commentaire avec l'ID " + id + " n'a pas été trouvé."));
    }

    // Creates a new commentary based on the provided request and returns the
    // created commentary as a CommentaryResponse DTO
    // Crée un nouveau commentaire basé sur la requête fournie et retourne le
    // commentaire créé sous forme de DTO `CommentaryResponse`
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
    // Récupère tous les commentaires, triés par date de publication
    public List<CommentaryResponse> getAllOrderedByDate() {
        return commentaryRepository.findAllByOrderByDateOfPublicationAsc()
                .stream()
                .map(this::toCommentaryResponse)
                .toList();
    }

    // Retrieves all commentaries for a given animal
    // Récupère tous les commentaires pour un animal donné
    public List<CommentaryResponse> findAllCommentaryByAnimalId(Long animalId) {
        return commentaryRepository.findByAnimalId(animalId)
                .stream()
                .map(this::toCommentaryResponse)
                .toList();
    }

    // Retrieves a single commentary by its id. Throws CommentaryNotFoundException
    // if not found
    // Récupère un seul commentaire par son identifiant. Lance une
    // `CommentaryNotFoundException` s'il n'est pas trouvé
    public CommentaryResponse findCommentaryById(String id) {
        return toCommentaryResponse(getCommentaryOrThrow(id));
    }

    // Updates an existing commentary
    // Met à jour un commentaire existant
    public CommentaryResponse updateCommentary(String id, CreateCommentaryRequest request) {
        CommentaryModel existingCommentary = getCommentaryOrThrow(id);

        existingCommentary.setTitle(request.getTitle());
        existingCommentary.setContent(request.getContent());
        existingCommentary.setDateOfPublication(request.getDateOfPublication());

        CommentaryModel updatedCommentary = commentaryRepository.save(existingCommentary);
        return toCommentaryResponse(updatedCommentary);
    }

    // Deletes a single commentary by its id
    // Supprime un seul commentaire par son identifiant
    public void deleteCommentary(String id) {
        if (!commentaryRepository.existsById(id)) {
            throw new CommentaryNotFoundException("Le commentaire avec l'ID " + id + " n'a pas été trouvé.");
        }
        commentaryRepository.deleteById(id);
    }

    // Deletes all commentaries for a given animal
    // Called by the Animal service when an animal is deleted
    // Supprime tous les commentaires pour un animal donné
    // Appelé par le service Animal lors de la suppression d'un animal
    public void deleteAllByAnimalId(Long animalId) {
        List<CommentaryModel> commentaries = commentaryRepository.findByAnimalId(animalId);
        commentaryRepository.deleteAll(commentaries);
    }
}