package com.onist.appointment.exception;

import java.time.Instant;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// Global exception handler for the appointment module. It catches specific exceptions thrown by the appointment module and
// returns a structured JSON response with an appropriate HTTP status code
// Gestionnaire d'exceptions global pour le module de rendez-vous. Il intercepte les exceptions spécifiques levées par le module de rendez-vous et
// renvoie une réponse JSON structurée avec un code d'état HTTP approprié
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles the case where an appointment is not found (404)
    // Gère le cas où un rendez-vous n'est pas trouvé (404)
    @ExceptionHandler(AppointmentNotFoundException.class)
    public ResponseEntity<Object> handleAppointmentNotFound(AppointmentNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Handles the case where an animal is not found (404)
    // Gère le cas où un animal n'est pas trouvé (404)
    @ExceptionHandler(AnimalNotFoundException.class)
    public ResponseEntity<Object> handleAnimalNotFound(AnimalNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Handles the case where a veterinarian is not found (404)
    // Gère le cas où un vétérinaire n'est pas trouvé (404)
    @ExceptionHandler(VeterinarianNotFoundException.class)
    public ResponseEntity<Object> handleVeterinarianNotFound(VeterinarianNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // Handles all unhandled exceptions (500 server error)
    // Gère toutes les exceptions non gérées (erreur serveur 500)
    private ResponseEntity<Object> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = Map.of(
                "timestamp", Instant.now().toString(),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message);
        return ResponseEntity.status(status).body(body);
    }
}
