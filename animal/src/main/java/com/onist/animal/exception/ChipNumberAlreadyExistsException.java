package com.onist.animal.exception;

// Exception thrown when trying to create an animal with a chip number that already exists in the system
public class ChipNumberAlreadyExistsException extends RuntimeException {
     public ChipNumberAlreadyExistsException(String message) {
        super(message);
    }
}
