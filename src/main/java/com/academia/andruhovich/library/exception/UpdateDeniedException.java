package com.academia.andruhovich.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class UpdateDeniedException extends RuntimeException {

    public UpdateDeniedException(String message) {
        super(message);
    }
}
