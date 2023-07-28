package com.solvedex.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserDoesntExistException extends RuntimeException {

    public UserDoesntExistException() {
        super();
    }

}