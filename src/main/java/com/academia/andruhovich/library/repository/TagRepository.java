package com.academia.andruhovich.library.repository;

import com.academia.andruhovich.library.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
