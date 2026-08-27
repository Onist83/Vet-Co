package com.onist.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.onist.user.exception.EmailAlreadyExistsException;
import com.onist.user.exception.UserNotFoundException;
import com.onist.user.model.Role;
import com.onist.user.model.UserModel;
import com.onist.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Creates a new user in the system after checking if the email already exists. If the email is unique, it encodes the password and saves the user to the repository
    public UserModel createUser(String email, String password, String firstName, String lastName, Role role) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Email already exists: " + email);
        }
        
        // Build a new UserModel object with the provided details, encoding the password for security
        UserModel user = UserModel.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .firstname(firstName)
                .lastname(lastName)
                .role(role)
                .enabled(true)
                .build();

        return userRepository.save(user);
    }

    // Retrieves a user by their unique Id. If the user is not found, it throws a UserNotFoundException
    public Optional<UserModel> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Retrieves a user by their unique Id. If the user is not found, it throws a UserNotFoundException
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }
    
    // Retrieves a user by their unique Id. If the user is not found, it throws a UserNotFoundException
    public UserModel updateUser(Long id, UserModel updatedUser) {
       UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        existingUser.setFirstname(updatedUser.getFirstname());
        existingUser.setLastname(updatedUser.getLastname());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());
    
        return userRepository.save(existingUser);
    }

    // Deletes a user by their unique Id. If the user is not found, it throws a UserNotFoundException
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }
}
