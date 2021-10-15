package com.academia.andruhovich.library.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column
	private String firstName;
	@Column
	private String lastName;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
	private List<Book> books;

}
