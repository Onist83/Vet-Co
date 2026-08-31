package com.onist.user.exception;

// Authentication failed(incorrect credentials, account deactivated, etc...)
public class HandleAuthenticationFailure extends RuntimeException {
    public HandleAuthenticationFailure(String message) {
        super(message);
    }
}
