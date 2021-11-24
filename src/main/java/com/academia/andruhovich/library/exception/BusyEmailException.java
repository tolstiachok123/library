package com.academia.andruhovich.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BusyEmailException extends RuntimeException {

    public BusyEmailException(String message) {
        super(message);
    }
}
