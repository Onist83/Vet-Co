package com.onist.user.exception;

// Custom exception class for handling cases where an operation is forbidden due to role hierarchy or permissions
public class ForbiddenOperationException extends RuntimeException{
    public ForbiddenOperationException(String message) {
        super(message);
    }
}
