package com.onist.user.exception;

// Custom exception class for handling cases where a refresh token is invalid or expired
public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}