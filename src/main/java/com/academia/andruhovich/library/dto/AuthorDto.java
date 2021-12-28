package com.academia.andruhovich.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {

	@Min(1)
	private Long id;
	@Size(min = 1, max = 255)
	private String firstName;
	@Size(min = 1, max = 255)
	private String lastName;

}
