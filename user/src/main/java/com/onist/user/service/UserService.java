package com.onist.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.onist.user.dto.CreateUserRequest;
import com.onist.user.dto.UpdateUserRequest;
import com.onist.user.dto.UserResponse;
import com.onist.user.exception.EmailAlreadyExistsException;
import com.onist.user.exception.ForbiddenOperationException;
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

    // Retrieves the currently authenticated user
    private UserModel getCurrentActingUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("L'utilisateur avec l'email: " + email + " n'as pas été trouvé"));
    }

    // Determines whether the "actingRole" role has the right to manage an account with the "targetRole" role
    private boolean canManage(Role actingRole, Role targetRole) {
        return switch (actingRole) {
            case ADMIN -> true;
            case SUPER_MANAGER -> targetRole == Role.MANAGER || targetRole == Role.USER;
            case MANAGER -> targetRole == Role.USER;
            case USER -> false;
        };
    }

    // Only the Admin and Lead Manager can change an account's role (promotion/demotion)
    private boolean canChangeRole(Role actingRole) {
        return actingRole == Role.ADMIN || actingRole == Role.SUPER_MANAGER;
    }

    // Creates a new user in the system after checking if the email already exists. If the email is unique, it encodes the password and saves the user to the repository
    public UserResponse createUser(CreateUserRequest request) {

    UserModel actingUser = getCurrentActingUser();

        if (!canManage(actingUser.getRole(), request.getRole())) {
            throw new ForbiddenOperationException(
                    "Vous n'avez pas le droit de créer un compte avec ce rôle"
            );
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "L'email existe déjà: " + request.getEmail()
            );
        }

        UserModel user = UserModel.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .role(request.getRole())
                .enabled(true)
                .build();

        UserModel savedUser = userRepository.save(user);

        return toUserResponse(savedUser);
    }

        private UserResponse toUserResponse(UserModel user) {

        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .mustChangePassword(user.isMustChangePassword())
                .build();
        }

    // Retrieves a user by their unique Id. If the user is not found, it throws a UserNotFoundException
    public Optional<UserModel> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Retrieves a user by their unique Id. If the user is not found, it throws a UserNotFoundException
    public List<UserResponse> getAllUsers() {

    return userRepository.findAll()
            .stream()
            .map(this::toUserResponse)
            .toList();
}
    // Retrieves a user by their unique Id. If the user is not found, it throws a UserNotFoundException
    public UserResponse updateUser(Long id, UpdateUserRequest request) {

    UserModel actingUser = getCurrentActingUser();

    UserModel existingUser = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException(
                    "L'utilisateur avec l'ID: " + id + " n'a pas été trouvé"
            ));

    if (!canManage(actingUser.getRole(), existingUser.getRole())) {
        throw new ForbiddenOperationException(
                "Vous n'avez pas le droit de modifier ce compte"
        );
    }

    existingUser.setFirstname(request.getFirstname());
    existingUser.setLastname(request.getLastname());

    if (!existingUser.getEmail().equalsIgnoreCase(request.getEmail())) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "L'email: " + request.getEmail() + " existe déjà"
            );
        }

        existingUser.setEmail(request.getEmail());
    }

    if (request.getRole() != existingUser.getRole()) {

        if (!canChangeRole(actingUser.getRole())) {
            throw new ForbiddenOperationException(
                    "Vous n'avez pas le droit de changer le rôle de ce compte"
            );
        }

        if (!canManage(actingUser.getRole(), request.getRole())) {
            throw new ForbiddenOperationException(
                    "Vous n'avez pas le droit d'attribuer ce rôle"
            );
        }

        existingUser.setRole(request.getRole());
    }

    UserModel savedUser = userRepository.save(existingUser);

    return toUserResponse(savedUser);
}

    // Deletes a user by their unique Id. If the user is not found, it throws a UserNotFoundException
    public void deleteUserById(Long id) {
        UserModel actingUser = getCurrentActingUser();
        UserModel target = userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("L'utilisateur avec l'ID: " + id + " n'as pas été trouvé"));

        if (!canManage(actingUser.getRole(), target.getRole())) {
            throw new ForbiddenOperationException("Vous n'avez pas le droit de supprimer ce compte");
        }
        userRepository.deleteById(id);
    }

    // Changes a user's password. Encodes the new password and 
    // disables the mustChangePassword flag
    public void changePassword(String email, String newPassword) {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("L'utilisateur avec l'email: " + email + " n'as pas été trouvé"));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setMustChangePassword(false);
        userRepository.save(user);
    }
}
