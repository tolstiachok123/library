package com.academia.andruhovich.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class BookDto {

	private Long id;
	private String title;
	private BigDecimal price;
	private String imageUrl;
	private Long authorId;
	private List<Long> tagIdList;
}
