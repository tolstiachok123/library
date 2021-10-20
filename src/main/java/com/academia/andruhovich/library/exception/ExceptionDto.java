package com.academia.andruhovich.library.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ExceptionDto {

	private int status;
	private String message;
	private String error;
	private ZonedDateTime time;

}
