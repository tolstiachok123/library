package com.academia.andruhovich.library.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
@Data
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column
	private String title;
	@Column
	private BigDecimal price;
	@Column
	private String imageUrl;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	private Author author;

	@ManyToMany(fetch = FetchType.LAZY)
	private List<Tag> tags;
	@CreatedDate
	private Date createdAt;
	@LastModifiedDate
	private Date updatedAt;
}
