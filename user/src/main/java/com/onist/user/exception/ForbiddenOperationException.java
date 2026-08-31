package com.onist.user.exception;

// The action is prohibited given his role
public class ForbiddenOperationException extends RuntimeException{
    public ForbiddenOperationException(String message) {
        super(message);
    }
}
