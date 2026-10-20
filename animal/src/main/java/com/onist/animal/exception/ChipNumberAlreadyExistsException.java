package com.onist.animal.exception;

// Exception thrown when trying to create an animal with a chip number that already exists in the system
// Exception levée lorsqu'on essaie de créer un animal avec un numéro de puce qui existe déjà dans le système
public class ChipNumberAlreadyExistsException extends RuntimeException {
    public ChipNumberAlreadyExistsException(String message) {
        super(message);
    }
}
