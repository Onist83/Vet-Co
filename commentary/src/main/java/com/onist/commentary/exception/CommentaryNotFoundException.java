package com.onist.commentary.exception;

// This class represents a custom exception that is thrown when a commentary is not found in the database
// Cette classe représente une exception personnalisée qui est levée lorsqu'un commentaire n'est pas trouvé dans la base de données
public class CommentaryNotFoundException extends RuntimeException {
    public CommentaryNotFoundException(String message) {
        super(message);
    }
}
