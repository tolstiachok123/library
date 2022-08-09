package com.academia.andruhovich.library.service.impl;

import com.academia.andruhovich.library.model.Message;
import com.academia.andruhovich.library.repository.BrokerRepository;
import com.academia.andruhovich.library.service.BrokerService;
import com.academia.andruhovich.library.util.DateHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BrokerServiceImpl implements BrokerService {

	private final BrokerRepository repository;

	@Transactional
	@Override
	public Message add(String messageString) {
		Message message = new Message();
		message.setMessage(messageString);
		message.setCreatedAt(DateHelper.currentDate());
		message.setUpdatedAt(DateHelper.currentDate());
		return repository.save(message);
	}

	@Cacheable(value = "messageCache")
	public Message getById(Long id) {
		return repository.getById(id);
	}
}
