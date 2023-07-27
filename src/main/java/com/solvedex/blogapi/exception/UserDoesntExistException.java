package com.solvedex.blogapi.exception;

public class UserDoesntExistException extends Exception {

    public UserDoesntExistException() {
        super();
    }

    public UserDoesntExistException(String message) {
        super(message);
    }

    public UserDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDoesntExistException(Throwable cause) {
        super(cause);
    }
}