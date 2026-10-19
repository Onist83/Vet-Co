package com.onist.animal.exception;

// Custom exception class for handling cases where an animal is not found in the system
public class AnimalNotFoundException extends RuntimeException{
    public AnimalNotFoundException(String message) {
        super(message);
    }
}
