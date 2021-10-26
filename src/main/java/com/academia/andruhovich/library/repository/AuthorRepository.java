package com.academia.andruhovich.library.repository;

import com.academia.andruhovich.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

	@Query("select a from Author a where a.id = :id")
	Optional<Author> getAuthorById(Long id);
}
