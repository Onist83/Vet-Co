package com.onist.user.exception;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Global exception handler for the application. It catches specific exceptions thrown by the application and
//  returns a structured JSON response with an appropriate HTTP status code
// Gestionnaire d'exceptions global pour l'application. Il intercepte les exceptions spécifiques levées par l'application et
//  renvoie une réponse JSON structurée avec un code d'état HTTP approprié
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handles all unhandled exceptions (500 server error)
    // Gère toutes les exceptions non gérées (erreur serveur 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpected(Exception ex) {
        log.error("Erreur inattendue", ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue");
    }

    // Handles the case where a user is not found (404)
    // Gère le cas où un utilisateur n'est pas trouvé (404)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFound(UserNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Handles the case where the email is already in use (409 Conflict)
    // Gère le cas où l'email est déjà utilisé (409 Conflit)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<Object> handleEmailExists(EmailAlreadyExistsException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    // Handles the case of an invalid or expired refresh token (401 Unauthorized)
    // Gère le cas d'un jeton de rafraîchissement invalide ou expiré (401 Non
    // autorisé)
    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<Object> handleInvalidRefreshToken(InvalidRefreshTokenException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    // An action prohibited by the role hierarchy (Ex: Manager interacting with an
    // Admin)
    // Une action interdite par la hiérarchie des rôles (Ex: Manager interagissant
    // avec un Admin)
    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<Object> handleAccessDenied(ForbiddenOperationException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    // Handles request validation errors (@Valid) → 400 Bad Request
    // Gère les erreurs de validation de requête (@Valid) → 400 Requête incorrecte
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

    // Handles authentication failures (invalid credentials, disabled account,
    // unknown user) → 401
    // Gère les échecs d'authentification (identifiants invalides, compte désactivé,
    // utilisateur inconnu) → 401
    @ExceptionHandler({ BadCredentialsException.class, UsernameNotFoundException.class, DisabledException.class })
    public ResponseEntity<Object> handleAuthenticationFailure(AuthenticationException ex) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "L' email ou le mot de passe est invalide");
    }

    // Method to construct a uniform JSON response
    // Méthode pour construire une réponse JSON uniforme
    private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now().toString(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message);
        return ResponseEntity.status(status).body(body);
    }
}