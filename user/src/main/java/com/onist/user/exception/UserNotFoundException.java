package com.onist.user.exception;

// Custom exception class for handling cases where a user is not found in the system
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}