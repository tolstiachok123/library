package com.academia.andruhovich.library.repository;

import com.academia.andruhovich.library.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Page<Author> findAllByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
}