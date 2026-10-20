package com.onist.user.security;

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

import com.onist.user.model.UserModel;
import com.onist.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// This filter intercepts incoming HTTP requests to authenticate users based on the JWT token provided in the Authorization header
// It checks the validity of the token, retrieves user details, and sets the authentication in the SecurityContext for further processing
// Ce filtre intercepte les requêtes HTTP entrantes afin d'authentifier les utilisateurs sur la base du jeton JWT fourni dans l'en-tête Authorization
// Il vérifie la validité du jeton, récupère les informations de l'utilisateur et définit l'authentification dans le SecurityContext en vue d'un traitement ultérieur
@Component
@RequiredArgsConstructor
public class JwtUserAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String CHANGE_PASSWORD_PATH = "/api/v1/auth/change-password";

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    // Filters incoming HTTP requests to authenticate users based on the JWT token
    // provided in the Authorization header
    // Filtre les requêtes HTTP entrantes afin d'authentifier les utilisateurs sur
    // la base du jeton JWT fourni dans l'en-tête Authorization
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(AUTHORIZATION_HEADER);

        // Check if the Authorization header is present and starts with "Bearer "
        // Vérifie si l'en-tête Authorization est présent et commence par "Bearer "
        if (header != null && header.startsWith(BEARER_PREFIX)) {
            String token = header.substring(BEARER_PREFIX.length());

            // Validate the token and check if it is not a refresh token before proceeding
            // with authentication
            // Valide le jeton et vérifie qu'il ne s'agit pas d'un jeton de rafraîchissement
            // avant de procéder à l'authentification
            if (jwtTokenProvider.validateToken(token) && !jwtTokenProvider.isRefreshToken(token)) {
                String email = jwtTokenProvider.getEmailFromToken(token);
                boolean isChangePasswordRequest = request.getRequestURI().equals(CHANGE_PASSWORD_PATH);

                // If the user is required to change their password and the current request is
                // not a password change request,
                // do not authenticate the user
                // Si l'utilisateur est tenu de changer son mot de passe et que la requête
                // actuelle n'est pas une requête de changement de mot de passe,
                // ne pas authentifier l'utilisateur
                userRepository.findByEmail(email)
                        .filter(UserModel::isEnabled)
                        .ifPresent(user -> {
                            if (user.isMustChangePassword() && !isChangePasswordRequest) {
                                return;
                            }

                            // Create a list of granted authorities based on the user's role extracted from
                            // the token
                            // Crée une liste d'autorisations accordées en fonction du rôle de l'utilisateur
                            // extrait du jeton
                            List<GrantedAuthority> authorities = List
                                    .of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

                            // Create an authentication token and set it in the SecurityContext to
                            // authenticate the user for the current request
                            // Crée un jeton d'authentification et le définit dans le SecurityContext pour
                            // authentifier l'utilisateur pour la requête en cours
                            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    email, null, authorities);
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        });
            }
        }
        filterChain.doFilter(request, response);
    }
}
