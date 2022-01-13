package com.academia.andruhovich.library.repository;

import com.academia.andruhovich.library.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> getByName(String name);
}
