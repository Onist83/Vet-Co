package com.onist.animal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onist.animal.model.AnimalModel;

// Repository interface for managing AnimalModel entities, providing methods for CRUD operations and custom queries
// Interface de dépôt pour la gestion des entités AnimalModel, fournissant des méthodes pour les opérations CRUD et des requêtes personnalisées
public interface AnimalRepository extends JpaRepository<AnimalModel, Long> {

    // Checks if an animal with the given chip number already exists in the database
    // Vérifie si un animal avec le numéro de puce donné existe déjà dans la base de
    // données
    boolean existsByChipNumber(String chipNumber);

    // Searches for animals based on a query string that matches the animal's name
    // or the owner's first and last names (case-insensitive)
    // Recherche des animaux en fonction d'une chaîne de requête qui correspond au
    // nom de l'animal ou aux prénoms et noms des propriétaires (insensible à la
    // casse)
    @Query("""
            SELECT a FROM AnimalModel a
            LEFT JOIN a.owner o
            LEFT JOIN a.secondOwner so
            WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(o.firstName) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(o.lastName) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(so.firstName) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(so.lastName) LIKE LOWER(CONCAT('%', :query, '%'))
            """)
    List<AnimalModel> searchByNameOrOwner(@Param("query") String query);
}
