package com.solvedex.blogapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotOwnerException extends RuntimeException {

    public NotOwnerException() {
        super();
    }

}