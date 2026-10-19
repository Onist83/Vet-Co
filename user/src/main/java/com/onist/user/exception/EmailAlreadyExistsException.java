package com.onist.user.exception;

// Exception thrown when trying to create a user with an email that already exists in the system
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}