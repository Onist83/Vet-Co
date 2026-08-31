package com.onist.user.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.onist.user.model.Role;
import com.onist.user.model.UserModel;
import com.onist.user.repository.UserRepository;



// User configuration + default account initialization
@Configuration
@Profile("dev")
public class UserConfiguration {

    @Value("${app.bootstrap.admin-password}")
    private String adminPassword;

    @Value("${app.bootstrap.super-manager-password}")
    private String superManagerPassword;

    @Value("${app.bootstrap.user-password}")
    private String userPassword;

    // Creates Admin, Manager, and User accounts at startup if they do not exist
    @Bean
    public CommandLineRunner createAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@vetetco.com").isEmpty()) {
               
            // Create Admin
                UserModel admin = new UserModel();
                admin.setEmail("admin@vetetco.com");
                admin.setPassword(passwordEncoder.encode(adminPassword));
                admin.setRole(Role.ADMIN);
                admin.setFirstname("Admin");
                admin.setLastname("Super");
                admin.setEnabled(true);

                userRepository.save(admin);
            }

            // Create Manager
            if (userRepository.findByEmail("manager@vetetco.com").isEmpty()) {
                UserModel manager = new UserModel();
                manager.setEmail("manager@vetetco.com");
                manager.setPassword(passwordEncoder.encode(superManagerPassword));
                manager.setRole(Role.SUPER_MANAGER);
                manager.setFirstname("Manager");
                manager.setLastname("Super");
                manager.setEnabled(true);

                userRepository.save(manager);
            }

            // Create User
            if (userRepository.findByEmail("user@vetetco.com").isEmpty()) {
                UserModel user = new UserModel();
                user.setEmail("user@vetetco.com");
                user.setPassword(passwordEncoder.encode(userPassword));
                user.setRole(Role.USER);
                user.setFirstname("User");
                user.setLastname("Regular");
                user.setEnabled(true);

                userRepository.save(user);
            }
        };
    }
}
