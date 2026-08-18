package com.onist.user.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.onist.user.model.Role;
import com.onist.user.model.UserModel;
import com.onist.user.repository.UserRepository;

@Configuration
public class UserConfiguration {

    @Bean
    public CommandLineRunner createAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@vetetco.com").isEmpty()) {
               
                UserModel admin = new UserModel();
                admin.setEmail("admin@vetetco.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRole(Role.ADMIN);
                admin.setFirstname("Admin");
                admin.setLastname("Super");
                admin.setEnabled(true);

                userRepository.save(admin);
            }

            if (userRepository.findByEmail("manager@vetetco.com").isEmpty()) {
                UserModel manager = new UserModel();
                manager.setEmail("manager@vetetco.com");
                manager.setPassword(passwordEncoder.encode("manager123"));
                manager.setRole(Role.MANAGER);
                manager.setFirstname("Manager");
                manager.setLastname("User");
                manager.setEnabled(true);

                userRepository.save(manager);
            }

            if (userRepository.findByEmail("user@vetetco.com").isEmpty()) {
                UserModel user = new UserModel();
                user.setEmail("user@vetetco.com");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setRole(Role.USER);
                user.setFirstname("User");
                user.setLastname("Regular");
                user.setEnabled(true);

                userRepository.save(user);
            }
        };
    }

}
