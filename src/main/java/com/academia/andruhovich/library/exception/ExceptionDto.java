package com.academia.andruhovich.library.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionDto {

	private int status;
	private String message;
	private String error;
	private ZonedDateTime time;

}
