package com.onist.user.configuration;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.onist.user.security.JwtUserAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

// Configuration class for setting up Spring Security in the application 
// It defines the security filter chain, authentication and authorization rules, 
// and custom error handling for unauthorized and forbidden access attempts
// La classe de configuration pour la mise en place de Spring Security dans l'application
//  Elle définit la chaîne de filtres de sécurité, les règles d'authentification et d'autorisation,
//  ainsi que la gestion des erreurs personnalisée pour les tentatives d'accès non autorisées et interdites
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class UserSecurityConfig {
    private final JwtUserAuthenticationFilter jwtUserAuthenticationFilter;
    private final ObjectMapper objectMapper;

    // Configures the security filter chain for the application, defining how
    // requests are secured and
    // which endpoints require authentication or specific roles
    // Configure la chaîne de filtres de sécurité pour l'application, en définissant
    // comment
    // les requêtes sont sécurisées et
    // quelles ressources endpoints nécessitent une authentification ou des rôles
    // spécifiques
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/actuator/health", "/actuator/health/**").permitAll()
                        .requestMatchers("/api/v1/auth/login").permitAll()
                        .requestMatchers("/api/v1/auth/refresh").permitAll()
                        .requestMatchers("/api/v1/auth/change-password").authenticated()
                        .requestMatchers("/api/v1/user/**")
                        .hasAnyRole("ADMIN", "SUPER_MANAGER", "MANAGER")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtUserAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Returns a 403 when an authenticated role lacks the necessary permissions
    // Renvoie un code 403 lorsque le rôle authentifié ne dispose pas des
    // autorisations nécessaires
    private AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) -> writeErrorResponse(
                response, HttpStatus.FORBIDDEN, "Vous n'avez pas les droits pour accéder à cette ressource");
    }

    // Returns a 401 JSON uniform when no valid authentication is provided
    // Renvoie un code 401 JSON uniforme lorsqu'aucune authentification valide n'est
    // fournie
    private AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, ex) -> writeErrorResponse(
                response, HttpStatus.UNAUTHORIZED, "Authentification requise");
    }

    // Writes a JSON error response with the specified HTTP status and message,
    // including a timestamp and error details
    // Écrit une réponse d'erreur JSON avec le statut HTTP et le message spécifiés,
    // incluant un horodatage et les détails de l'erreur
    private void writeErrorResponse(HttpServletResponse response, HttpStatus status, String message)
            throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now().toString(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message);
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }

    // Defines a bean for password encoding using BCrypt, which is used to securely
    // hash user passwords before storing them in the database
    // Définit un bean pour le codage des mots de passe en utilisant BCrypt, qui est
    // utilisé pour
    // hacher de manière sécurisée les mots de passe des utilisateurs avant de les
    // stocker dans la base de données
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Defines a bean for the AuthenticationManager, which is responsible for
    // processing authentication requests and validating user credentials
    // Définit un bean pour l'AuthenticationManager, qui est responsable du
    // traitement
    // des demandes d'authentification et de la validation des informations
    // d'identification des utilisateurs
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}
