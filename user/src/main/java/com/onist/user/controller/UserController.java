package com.onist.user.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.onist.user.dto.CreateUserRequest;
import com.onist.user.dto.UpdateUserRequest;
import com.onist.user.dto.UserResponse;
import com.onist.user.service.UserService;

import lombok.RequiredArgsConstructor;

// The UserController class is a REST controller that handles HTTP requests related to user management operations
// It provides endpoints for creating, updating, deleting, and retrieving user entities, as well as searching for users by name and role
// La classe UserController est un contrôleur REST qui gère les requêtes HTTP liées aux opérations de gestion des utilisateurs
// Elle propose des points de terminaison permettant de créer, mettre à jour, supprimer et récupérer des entités utilisateur, ainsi que de rechercher des utilisateurs par nom et par rôle
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Handles the creation of a new user entity. It accepts a POST request with a
    // CreateUserRequest payload,
    // validates it, and returns the created UserResponse with a 201 Created status
    // Gère la création d'une nouvelle entité utilisateur. Elle accepte une requête
    // POST avec une charge utile CreateUserRequest,
    // la valide et renvoie le UserResponse créé avec un statut 201 Created
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        UserResponse created = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Handles the search for user entities by name. It accepts a GET request with a
    // query parameter and
    // returns a list of UserResponse objects that match the search criteria with a
    // 200 OK status
    // Gère la recherche d'entités utilisateur par nom. Elle accepte une requête GET
    // avec un paramètre de requête et
    // renvoie une liste d'objets UserResponse correspondant aux critères de
    // recherche avec un statut 200 OK
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsers(@RequestParam String query) {
        return ResponseEntity.ok(userService.searchUsers(query));
    }

    // Retrieves all user entities. It accepts a GET request and
    // returns a list of UserResponse objects with a 200 OK status
    // Récupère toutes les entités utilisateur. Elle accepte une requête GET et
    // renvoie une liste d'objets UserResponse avec un statut 200 OK
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // Retrieves a single user entity by its ID. It accepts a GET request with a
    // path variable for the
    // user ID and returns the matching UserResponse with a 200 OK status
    // Récupère une seule entité utilisateur par son ID. Elle accepte une requête
    // GET avec une variable de chemin pour l'ID de l'utilisateur et renvoie le
    // UserResponse correspondant avec un statut 200 OK
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Updates an existing user entity. It accepts a PUT request with a path
    // variable for the user ID and
    // a UpdateUserRequest payload, validates it, and returns the updated
    // UserResponse with a 200 OK status
    // Met à jour une entité utilisateur existante. Elle accepte une requête PUT
    // avec une variable de chemin pour l'ID de l'utilisateur et une charge utile
    // UpdateUserRequest, la valide et renvoie le UserResponse mis à jour avec un
    // statut 200 OK
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserRequest request) {

        UserResponse updated = userService.updateUser(id, request);

        return ResponseEntity.ok(updated);
    }

    // Deletes an existing user entity by its ID. It accepts a DELETE request with a
    // path variable for the user ID
    // and returns a 204 No Content status if the deletion is successful
    // Supprime une entité utilisateur existante par son ID. Elle accepte une
    // requête DELETE avec une variable de chemin pour l'ID de l'utilisateur et
    // renvoie un statut 204 No Content si la suppression est réussie
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }
}
