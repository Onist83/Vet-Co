package com.onist.user.exception;

// Custom exception class for handling cases where a refresh token is invalid or expired
// Exception personnalisée pour gérer les cas où un jeton de rafraîchissement est invalide ou expiré
public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}