package com.onist.animal.exception;

// Custom exception class for handling cases where an animal is not found in the system
// Classe d'exception personnalisée pour gérer les cas où un animal n'est pas trouvé dans le système
public class AnimalNotFoundException extends RuntimeException {
    public AnimalNotFoundException(String message) {
        super(message);
    }
}
