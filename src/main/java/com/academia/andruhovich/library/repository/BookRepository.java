package com.academia.andruhovich.library.repository;

import com.academia.andruhovich.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
