package com.onist.user.exception;

// Exception thrown when trying to create a user with an email that already exists in the system
// Exception levée lors de la tentative de création d'un utilisateur avec une adresse e-mail qui existe déjà dans le système
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}