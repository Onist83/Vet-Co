package com.onist.animal.security;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

// Filter that intercepts incoming HTTP requests to validate JWT tokens and set the authentication context
// Filtre qui intercepte les requêtes HTTP entrantes pour valider les jetons JWT et définir le contexte d'authentification
@Component
@RequiredArgsConstructor
public class JwtAnimalAuthenticationFilter extends OncePerRequestFilter {

    // Constants for the Authorization header and Bearer token prefix
    // Constantes pour l'en-tête d'autorisation et le préfixe du jeton Bearer
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtValidator jwtValidator;

    // Method that filters incoming requests to validate JWT tokens and set the
    // authentication context
    // Méthode qui filtre les requêtes entrantes pour valider les jetons JWT et
    // définir le contexte d'authentification
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(AUTHORIZATION_HEADER);

        if (header != null && header.startsWith(BEARER_PREFIX)) {
            String token = header.substring(BEARER_PREFIX.length());

            if (jwtValidator.validateToken(token)) {
                String email = jwtValidator.getEmailFromToken(token);
                String role = jwtValidator.getRoleFromToken(token);

                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,
                        null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}