package com.onist.commentary.security;

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

// This configuration class sets up the security settings for the application
// It configures the security filter chain, including disabling CSRF protection,
// setting the session management policy to stateless, and defining the authorization rules for different endpoints
// Cette classe de configuration met en place les paramètres de sécurité pour l'application
// Elle configure la chaîne de filtres de sécurité, y compris la désactivation de la protection CSRF,
// la définition de la politique de gestion des sessions comme étant sans état, et la définition des règles d'autorisation pour différents points de terminaison
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class CommentarySecurityConfig {

    private final JwtCommentaryAuthenticationFilter jwtCommentaryAuthenticationFilter;

    // Configures the security filter chain for the application
    // Configure la chaîne de filtres de sécurité pour l'application
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/actuator/health", "/actuator/health/**").permitAll()
                        .requestMatchers("/api/v1/commentary/**").authenticated()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtCommentaryAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
