package com.onist.appointment.exception;

public class VeterinarianNotFoundException extends RuntimeException {
    public VeterinarianNotFoundException(String message) {
        super(message);
    }
}
