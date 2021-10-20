package com.academia.andruhovich.library.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZonedDateTime;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(value = Throwable.class)
	public ResponseEntity<?> handle(Throwable throwable) {
		log.error("Caught unhandled exception: ", throwable.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<?> handle(ResourceNotFoundException exception) {
		log.error("Caught ResourceNotFoundException: " + exception.getMessage());
		ExceptionDto exceptionDto = ExceptionDto.builder()
				.status(HttpStatus.NOT_FOUND.value())
				.error(HttpStatus.NOT_FOUND.getReasonPhrase())
				.message(exception.getMessage())
				.time(ZonedDateTime.now())
				.build();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionDto);
	}
}
