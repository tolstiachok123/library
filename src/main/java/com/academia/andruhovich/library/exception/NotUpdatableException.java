package com.academia.andruhovich.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class NotUpdatableException extends RuntimeException {

    public NotUpdatableException(String message) {
        super(message);
    }
}
