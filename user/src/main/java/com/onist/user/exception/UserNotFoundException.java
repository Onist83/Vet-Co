package com.onist.user.exception;

// Custom exception class for handling cases where a user is not found in the system
// Classe d'exception personnalisée pour gérer les cas où un utilisateur n'est pas trouvé dans le système
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}