package com.academia.andruhovich.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

	@Min(1)
	private Long id;
	@Pattern(regexp = "[A-ZА-Я][a-zа-я]*[-\\s]?([A-ZА-Я][a-zа-я]*[-\\s]?)*")
	private String firstName;
	@Pattern(regexp = "[A-ZА-Я][a-zа-я]*[-\\s]?([A-ZА-Я][a-zа-я]*[-\\s]?)*")
	private String lastName;

	AuthorDto(Long id) {
		this.id = id;
	}
}
