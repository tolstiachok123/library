package com.academia.andruhovich.library.repository;

import com.academia.andruhovich.library.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
