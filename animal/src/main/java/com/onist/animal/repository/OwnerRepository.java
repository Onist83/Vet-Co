package com.onist.animal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onist.animal.model.OwnerModel;

public interface OwnerRepository extends JpaRepository<OwnerModel, Long> {
    Optional<OwnerModel> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
}