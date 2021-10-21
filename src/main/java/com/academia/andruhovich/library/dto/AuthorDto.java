package com.academia.andruhovich.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class AuthorDto {

	@Pattern(regexp = "\\d*")
	private Long id;
	@Pattern(regexp = "[A-ZА-Я][a-zа-я]*[-\\s]?([A-ZА-Я][a-zа-я]*[-\\s]?)*")
	private String firstName;
	@Pattern(regexp = "[A-ZА-Я][a-zа-я]*[-\\s]?([A-ZА-Я][a-zа-я]*[-\\s]?)*")
	private String lastName;
}
