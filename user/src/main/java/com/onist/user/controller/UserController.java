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

    // Create a new User (Admin, Manager, or User)
    @PostMapping("/create")
   public ResponseEntity<UserResponse> createUser(
        @Valid @RequestBody CreateUserRequest request) {

    UserResponse created = userService.createUser(request);

    return ResponseEntity.status(HttpStatus.CREATED).body(created);
}
    
    //  Retrieves the list of all Users
    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> findAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
}

    // Updates the information of an existing User
    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponse> updateUser(
        @PathVariable Long id,
        @Valid @RequestBody UpdateUserRequest request) {

    UserResponse updated = userService.updateUser(id, request);

    return ResponseEntity.ok(updated);
}

    // Deletes a User by their Id
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUserById(id);

        return ResponseEntity.noContent().build();
    }
}
