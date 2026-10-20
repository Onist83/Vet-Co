package com.onist.user.exception;

// Custom exception class for handling cases where an operation is forbidden due to role hierarchy or permissions
// Cette classe d'exception personnalisée est utilisée pour gérer les cas où une opération est interdite en raison de la hiérarchie des rôles ou des autorisations
public class ForbiddenOperationException extends RuntimeException {
    public ForbiddenOperationException(String message) {
        super(message);
    }
}
