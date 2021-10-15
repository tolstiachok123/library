package com.academia.andruhovich.library.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Data
public class Tag {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column
	private String name;
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Book> books;

}
