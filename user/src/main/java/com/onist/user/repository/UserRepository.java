package com.onist.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.onist.user.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel,Integer> {

    // Finds a user by their email address, returning an Optional containing the UserModel if found, or an empty Optional if not found.
    Optional<UserModel> findByEmail(String email);
    boolean existsByEmail(String email);


}
