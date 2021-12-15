package com.academia.andruhovich.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidJsonException extends RuntimeException {

    public InvalidJsonException(String message) {
        super(message);
    }
}
