package com.academia.andruhovich.library.repository;

import com.academia.andruhovich.library.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	Set<Order> getAllByUserId(Long userId);

	Optional<Order> getByIdAndUserId(Long id, Long userId);

}
