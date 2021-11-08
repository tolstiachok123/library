package com.academia.andruhovich.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {

	@Min(1)
	private Long id;
	@Pattern(regexp = "[A-ZА-Я][a-zа-я]*[-\\s]?([A-ZА-Я]?[a-zа-я]*[-\\s]?)*")
	private String title;
	private BigDecimal price;
	private String imageUrl;
	@NotNull
	private AuthorDto author;
	private Set<TagDto> tags;
	private ZonedDateTime createdAt;
	private ZonedDateTime updatedAt;
}
