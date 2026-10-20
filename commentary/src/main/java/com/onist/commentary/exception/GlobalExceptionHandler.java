package com.onist.commentary.exception;

import java.time.Instant;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// This class handles exceptions globally across the application.
// It provides specific handlers for different types of exceptions,
// including handling of commentary not found exceptions and request validation errors.
// Cette classe gère les exceptions de manière globale dans l'application.
// Elle fournit des gestionnaires spécifiques pour différents types d'exceptions,
// y compris la gestion des exceptions de commentaire non trouvé et des erreurs de validation de requêtes.
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handles the case where a commentary is not found (404)
    // Gère le cas où un commentaire n'est pas trouvé (404)
    @ExceptionHandler(CommentaryNotFoundException.class)
    public ResponseEntity<Object> handleCommentaryNotFound(CommentaryNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Handles request validation errors (@Valid) → 400 Bad Request
    // Gère les erreurs de validation de requête (@Valid) → 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationErrors(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

    // Handles all unhandled exceptions (500 server error)
    // Gère toutes les exceptions non gérées (erreur serveur 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUnexpected(Exception ex) {
        log.error("Erreur inattendue", ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Une erreur inattendue est survenue");
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
