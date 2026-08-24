package com.onist.user.exception;

// Refresh token invalid or expired
public class InvalidRefreshTokenException extends RuntimeException {
    public InvalidRefreshTokenException(String message) {
        super(message);
    }
}