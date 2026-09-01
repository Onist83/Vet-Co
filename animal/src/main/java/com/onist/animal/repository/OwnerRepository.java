package com.onist.animal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onist.animal.model.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
}