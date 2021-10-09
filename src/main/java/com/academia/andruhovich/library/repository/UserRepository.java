package com.academia.andruhovich.library.repository;

import com.academia.andruhovich.library.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
