package com.onist.user.controller;

import java.util.List;

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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // Handles the creation of a new user entity. It accepts a POST request with a CreateUserRequest payload, 
    // validates it, and returns the created UserResponse with a 201 Created status
    @PostMapping("/create")
    @ResponseStatus (HttpStatus.CREATED)
   public ResponseEntity<UserResponse> createUser(
        @Valid @RequestBody CreateUserRequest request) {

        UserResponse created = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
}

    // Handles the search for user entities by name. It accepts a GET request with a query parameter and
    //  returns a list of UserResponse objects that match the search criteria with a 200 OK status
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsers(@RequestParam String query) {
        return ResponseEntity.ok(userService.searchUsers(query));
    }
    
     // Retrieves all user entities. It accepts a GET request and 
     // returns a list of UserResponse objects with a 200 OK status
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
}

    // Updates an existing user entity. It accepts a PUT request with a path variable for the user ID and
    //  a UpdateUserRequest payload, validates it, and returns the updated UserResponse with a 200 OK status
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(
        @PathVariable Long id,
        @Valid @RequestBody UpdateUserRequest request) {

        UserResponse updated = userService.updateUser(id, request);

        return ResponseEntity.ok(updated);
}

    // Deletes an existing user entity by its ID. It accepts a DELETE request with a path variable for the user ID
    //  and returns a 204 No Content status if the deletion is successful
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }
}
