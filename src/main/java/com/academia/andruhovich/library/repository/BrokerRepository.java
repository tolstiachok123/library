package com.academia.andruhovich.library.repository;

import com.academia.andruhovich.library.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrokerRepository extends JpaRepository<Message, Long> {
}
