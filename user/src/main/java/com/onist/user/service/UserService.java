package com.onist.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public UserModel createUser(String email, String password, String firstName, String lastName, Role role) {
        UserModel actingUser = getCurrentActingUser();

        if (!canManage(actingUser.getRole(), role)) {
            throw new ForbiddenOperationException("Vous n'avez pas le droit de créer un compte avec ce rôle");
        }

        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("L'email existe déjà: " + email);
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
        UserModel actingUser = getCurrentActingUser();
        UserModel existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("L'utilisateur avec l'ID: " + id + " n'as pas été trouvé"));

        if (!canManage(actingUser.getRole(), existingUser.getRole())) {
                    throw new ForbiddenOperationException("Vous n'avez pas le droit de modifier ce compte");
                }

        existingUser.setFirstname(updatedUser.getFirstname());
        existingUser.setLastname(updatedUser.getLastname());

        // Email modifiable uniquement par quelqu'un habilité à gérer ce compte (déjà vérifié ci-dessus)
        if (!existingUser.getEmail().equalsIgnoreCase(updatedUser.getEmail())) {
            if (userRepository.existsByEmail(updatedUser.getEmail())) {
                throw new EmailAlreadyExistsException("L'email: " + updatedUser.getEmail() + " existe déjà");
            }
            existingUser.setEmail(updatedUser.getEmail());
        }

        // Role change = promotion/demotion (Reserved for Admin and Lead Manager)
        if (updatedUser.getRole() != existingUser.getRole()) {
            if (!canChangeRole(actingUser.getRole())) {
                throw new ForbiddenOperationException("Vous n'avez pas le droit de changer le rôle de ce compte");
            }
            if (!canManage(actingUser.getRole(), updatedUser.getRole())) {
                throw new ForbiddenOperationException("Vous n'avez pas le droit d'attribuer ce rôle");
            }
            existingUser.setRole(updatedUser.getRole());
        }
        
        return userRepository.save(existingUser);
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
