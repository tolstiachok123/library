package com.academia.andruhovich.library.exception;

import com.academia.andruhovich.library.exception.error.FieldValidationError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = Throwable.class)
	public ResponseEntity<Void> handle(Throwable throwable) {
		log.error("Caught unhandled exception: {}", throwable.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<?> handle(AccessDeniedException ex) {
		log.error("Caught AccessDeniedException: {}", ex.getMessage());
		ExceptionDto exceptionDto = ExceptionDto.builder()
				.status(HttpStatus.FORBIDDEN.value())
				.error(HttpStatus.FORBIDDEN.getReasonPhrase())
				.message(ex.getMessage())
				.time(ZonedDateTime.now())
				.build();
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(exceptionDto);
	}

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<?> handle(ResourceNotFoundException exception) {
		log.error("Caught ResourceNotFoundException: {}", exception.getMessage());
		ExceptionDto exceptionDto = ExceptionDto.builder()
				.status(HttpStatus.NOT_FOUND.value())
				.error(HttpStatus.NOT_FOUND.getReasonPhrase())
				.message(exception.getMessage())
				.time(ZonedDateTime.now())
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDto);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {
		log.error("Caught MethodArgumentNotValidException: {}", exception.getMessage());
		BindingResult result = exception.getBindingResult();
		final FieldValidationError error = FieldValidationError.builder()
				.status(HttpStatus.UNPROCESSABLE_ENTITY)
				.message(ErrorMessages.INVALID_METHOD_ARGUMENT)
				.build();
		result.getFieldErrors().forEach(fieldError -> error
				.addFieldError(fieldError.getObjectName(),
						fieldError.getField(),
						fieldError.getDefaultMessage())
		);
		return new ResponseEntity<>(error, error.getStatus());
	}
}
