package com.onist.user.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.onist.user.model.UserModel;
import com.onist.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserCustomDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    // Loads user details by email, which is used for authentication and authorization in the application
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("L'utilisateur avec l'email: " + email + " n'as pas été trouvé"));
        
        // Builds a UserDetails object using the user's email, password, and role, which is used by Spring Security for authentication and authorization
        return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .disabled(!user.isEnabled())
                .build();
    }
}
