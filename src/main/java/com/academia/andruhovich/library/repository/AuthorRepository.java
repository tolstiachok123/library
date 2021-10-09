package com.academia.andruhovich.library.repository;

import com.academia.andruhovich.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
