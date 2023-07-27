package com.solvedex.blogapi.exception;

public class DataDoesntExistException extends Exception {

    public DataDoesntExistException() {
        super();
    }

    public DataDoesntExistException(String message) {
        super(message);
    }

    public DataDoesntExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataDoesntExistException(Throwable cause) {
        super(cause);
    }
}
