package com.academia.andruhovich.library.exception;

import com.academia.andruhovich.library.exception.error.FieldValidationError;
import com.academia.andruhovich.library.util.DateHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.academia.andruhovich.library.exception.ErrorMessages.INVALID_METHOD_ARGUMENT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<Void> handle(Throwable throwable) {
        log.error("Caught unhandled exception: {}", throwable.getMessage());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(value = InvalidJsonException.class)
    public ResponseEntity<Object> handle(InvalidJsonException ex) {
        log.error("Caught NotUpdatableException: {}", ex.getMessage());
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .status(UNPROCESSABLE_ENTITY.value())
                .error(UNPROCESSABLE_ENTITY.getReasonPhrase())
                .message(ex.getMessage())
                .time(DateHelper.currentDate())
                .build();
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(exceptionDto);
    }

    @ExceptionHandler(value = UpdateDeniedException.class)
    public ResponseEntity<Object> handle(UpdateDeniedException ex) {
        log.error("Caught NotUpdatableException: {}", ex.getMessage());
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .status(UNPROCESSABLE_ENTITY.value())
                .error(UNPROCESSABLE_ENTITY.getReasonPhrase())
                .message(ex.getMessage())
                .time(DateHelper.currentDate())
                .build();
        return ResponseEntity.status(UNPROCESSABLE_ENTITY).body(exceptionDto);
    }

    @ExceptionHandler(value = BusyEmailException.class)
    public ResponseEntity<Object> handle(BusyEmailException ex) {
        log.error("Caught BusyEmailException: {}", ex.getMessage());
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .status(BAD_REQUEST.value())
                .error(BAD_REQUEST.getReasonPhrase())
                .message(ex.getMessage())
                .time(DateHelper.currentDate())
                .build();
        return ResponseEntity.status(BAD_REQUEST).body(exceptionDto);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseEntity<Object> handle(AccessDeniedException ex) {
        log.error("Caught AccessDeniedException: {}", ex.getMessage());
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .status(FORBIDDEN.value())
                .error(FORBIDDEN.getReasonPhrase())
                .message(ex.getMessage())
                .time(DateHelper.currentDate())
                .build();
        return ResponseEntity.status(FORBIDDEN).body(exceptionDto);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<Object> handle(ResourceNotFoundException exception) {
        log.error("Caught ResourceNotFoundException: {}", exception.getMessage());
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .status(NOT_FOUND.value())
                .error(NOT_FOUND.getReasonPhrase())
                .message(exception.getMessage())
                .time(DateHelper.currentDate())
                .build();
        return ResponseEntity.status(NOT_FOUND).body(exceptionDto);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handle(AuthenticationException ex) {
        log.error("Caught AuthenticationException: {}", ex.getMessage());
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .status(UNAUTHORIZED.value())
                .error(UNAUTHORIZED.getReasonPhrase())
                .message(ex.getMessage())
                .time(DateHelper.currentDate())
                .build();
        return ResponseEntity.status(UNAUTHORIZED).body(exceptionDto);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
        log.error("Caught MethodArgumentNotValidException: {}", exception.getMessage());
        BindingResult result = exception.getBindingResult();
        final FieldValidationError error = FieldValidationError.builder()
                .status(UNPROCESSABLE_ENTITY)
                .message(INVALID_METHOD_ARGUMENT)
                .build();
        result.getFieldErrors().forEach(fieldError -> error
				.addFieldError(fieldError.getObjectName(),
						fieldError.getField(),
						fieldError.getDefaultMessage())
		);
		return new ResponseEntity<>(error, error.getStatus());
	}
}
