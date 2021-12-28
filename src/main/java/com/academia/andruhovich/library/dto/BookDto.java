package com.academia.andruhovich.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class BookDto {

	private Long id;
	@Size(min = 1, max = 255)
	private String title;
	private BigDecimal price;
	private String imageUrl;
	@NotNull
	private AuthorDto author;
	private Set<TagDto> tags;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
}
