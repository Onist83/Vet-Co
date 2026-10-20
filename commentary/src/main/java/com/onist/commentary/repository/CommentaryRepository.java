package com.onist.commentary.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.onist.commentary.model.CommentaryModel;

// This interface defines the repository for managing commentary data in MongoDB
// It extends the MongoRepository interface, providing CRUD operations and custom query methods for the CommentaryModel entity
// Cette interface définit le référentiel pour la gestion des données de commentaires dans MongoDB
// Elle étend l'interface MongoRepository, fournissant des opérations CRUD et des méthodes de requête personnalisées pour l'entité CommentaryModel
public interface CommentaryRepository extends MongoRepository<CommentaryModel, String> {

    // Retrieves all commentaries, ordered by date of publication
    // Récupère tous les commentaires, triés par date de publication
    List<CommentaryModel> findAllByOrderByDateOfPublicationAsc();

    // Retrieves all commentaries for a given animal
    // Récupère tous les commentaires pour un animal donné
    List<CommentaryModel> findByAnimalId(Long animalId);
}
