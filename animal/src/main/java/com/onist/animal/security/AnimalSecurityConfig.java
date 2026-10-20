package com.onist.animal.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

// Configuration class for setting up security in the Animal application. It defines the security filter chain, 
// including JWT authentication and authorization rules.
// Classe de configuration pour la mise en place de la sécurité dans l'application Animal.
//  Elle définit la chaîne de filtres de sécurité, y compris l'authentification JWT et les règles d'autorisation.
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AnimalSecurityConfig {

    private final JwtAnimalAuthenticationFilter jwtAnimalAuthenticationFilter;

    // Configuration method for setting up the security filter chain
    // Méthode de configuration pour la mise en place de la chaîne de filtres de
    // sécurité
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/actuator/health", "/actuator/health/**").permitAll()
                        .requestMatchers("/api/v1/animal/**").authenticated()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAnimalAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}