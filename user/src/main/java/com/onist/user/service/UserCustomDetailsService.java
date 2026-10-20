package com.onist.user.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.onist.user.model.UserModel;
import com.onist.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// The UserCustomDetailsService class implements the UserDetailsService interface, which is used by Spring Security to load user-specific data during authentication
// It retrieves user details from the UserRepository based on the provided email and constructs a UserDetails object that contains the user's email, password, and role for authentication and authorization purposes
// La classe UserCustomDetailsService implémente l'interface UserDetailsService, qui est utilisée par Spring Security pour charger les données spécifiques à l'utilisateur lors de l'authentification
// Elle récupère les détails de l'utilisateur à partir du UserRepository en fonction de l'e-mail fourni et construit un objet UserDetails qui contient l'e-mail, le mot de passe et le rôle de l'utilisateur à des fins d'authentification et d'autorisation
@Service
@RequiredArgsConstructor
public class UserCustomDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    // Loads user details by email, which is used for authentication and
    // authorization in the application
    // Charge les détails de l'utilisateur par e-mail, qui sont utilisés pour
    // l'authentification et l'autorisation dans l'application
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "L'utilisateur avec l'email: " + email + " n'as pas été trouvé"));

        // Builds a UserDetails object using the user's email, password, and role, which
        // is used by Spring Security for authentication and authorization
        // Construit un objet UserDetails en utilisant l'e-mail, le mot de passe et le
        // rôle de l'utilisateur, qui est utilisé par Spring Security pour
        // l'authentification et l'autorisation
        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .disabled(!user.isEnabled())
                .build();
    }
}
