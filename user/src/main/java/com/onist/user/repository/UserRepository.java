package com.onist.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.onist.user.model.UserModel;

// The UserRepository interface provides methods for accessing and manipulating user data in the database
// L'interface UserRepository fournit des méthodes pour accéder et manipuler les données des utilisateurs dans la base de données
public interface UserRepository extends JpaRepository<UserModel, Long> {

    // Finds a user by their email address, returning an Optional containing the
    // UserModel if found, or an empty Optional if not found
    // Recherche un utilisateur par son adresse e-mail, renvoyant un Optional
    // contenant le UserModel si trouvé, ou un Optional vide si non trouvé
    Optional<UserModel> findByEmail(String email);

    boolean existsByEmail(String email);

    // Searches for users whose first name, last name, or email contains the
    // specified query string (case-insensitive)
    // Recherche des utilisateurs dont le prénom, le nom de famille ou l'e-mail
    // contient la chaîne de requête spécifiée (insensible à la casse)
    @Query("""
            SELECT a FROM UserModel a
            WHERE LOWER(a.firstname) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(a.lastname) LIKE LOWER(CONCAT('%', :query, '%'))
                OR LOWER(a.email) LIKE LOWER(CONCAT('%', :query, '%'))
            """)
    List<UserModel> searchUsers(@Param("query") String query);
}
