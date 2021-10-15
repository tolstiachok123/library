package com.academia.andruhovich.library.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column
	private String firstName;
	@Column
	private String lastName;
	@Column
	private String email;
	@Column
	private String password;
	@CreatedDate
	private Date createdAt;
	@LastModifiedDate
	private Date updatedAt;

}
