package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.model.Message;
import com.academia.andruhovich.library.service.BrokerService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_READ;
import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_WRITE;

@CrossOrigin(origins = "http://localhost:3000/", maxAge = 3600)
@RestController
@RequestMapping("/api/rabbitmq")
@RequiredArgsConstructor
public class BrokerController {

    private final AmqpTemplate template;
    private final BrokerService service;

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('" + AUTHORITY_READ + "')")
    public Message getMessage(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/{message}")
    @PreAuthorize("hasAuthority('" + AUTHORITY_WRITE + "')")
    public Message createMessage(@PathVariable String message) {
        template.convertAndSend("queue1", message);
        return service.add(message);
    }

}
