package com.academia.andruhovich.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.JoinTable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.GenerationType;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user")
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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_order", joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "order_id")})
    private Set<Order> orders = new HashSet<>();

    @Column(name = "password")
    private String password;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

	public User(Long id, String email, Set<Role> roles, String password) {
		this.id = id;
		this.email = email;
		this.roles = roles;
		this.password = password;
	}
}
