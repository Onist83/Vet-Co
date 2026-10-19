package com.onist.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onist.user.model.UserModel;

public interface UserRepository extends JpaRepository<UserModel,Long> {

    // Finds a user by their email address, returning an Optional containing the UserModel if found, or an empty Optional if not found
    Optional<UserModel> findByEmail(String email);
    boolean existsByEmail(String email);

    // Searches for users whose first name, last name, or email contains the specified query string (case-insensitive)
    @Query("""
            SELECT a FROM UserModel a
            WHERE LOWER(a.firstname) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(a.lastname) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(a.email) LIKE LOWER(CONCAT('%', :query, '%'))
            """)
            List<UserModel> searchUsers(@Param("query") String query);
}
