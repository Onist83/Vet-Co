package com.onist.animal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onist.animal.model.OwnerModel;

// Repository interface for managing OwnerModel entities, providing methods to find owners by first and last name (case-insensitive)
// Interface de dépôt pour gérer les entités OwnerModel, fournissant des méthodes pour trouver des propriétaires par prénom et nom de famille (insensible à la casse)
public interface OwnerRepository extends JpaRepository<OwnerModel, Long> {
    Optional<OwnerModel> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
}