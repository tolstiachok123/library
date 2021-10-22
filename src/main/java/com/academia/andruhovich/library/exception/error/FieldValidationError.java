package com.academia.andruhovich.library.exception.error;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class FieldValidationError {

	private String message;
	private HttpStatus status;

	@Builder.Default
	private List<FieldError> fieldErrors = new ArrayList<>();

	public void addFieldError(String object, String path, String message) {
		FieldError error = new FieldError(object, path, message);
		fieldErrors.add(error);
	}
}
