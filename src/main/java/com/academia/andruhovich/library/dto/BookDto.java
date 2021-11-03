package com.academia.andruhovich.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

	private Long id;
	private String title;
	private BigDecimal price;
	private String imageUrl;
	private AuthorDto author;
	private Set<TagDto> tags;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
}
