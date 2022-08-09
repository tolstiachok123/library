package com.academia.andruhovich.library.service;

import com.academia.andruhovich.library.model.Message;

public interface BrokerService {

	Message add(String message);

	Message getById(Long id);
}
