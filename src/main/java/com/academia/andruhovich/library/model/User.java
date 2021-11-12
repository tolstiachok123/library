package com.academia.andruhovich.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
//@EntityListeners(AuditingEntityListener.class)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

//	@Column(name = "role")
//	@Enumerated(EnumType.STRING)
//	private Role role;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id")})
	private Set<Role> roles;

	@Column(name = "password")
	private String password;

	//	@CreatedDate
	private ZonedDateTime createdAt;

	//	@LastModifiedDate
	private ZonedDateTime updatedAt;
}
