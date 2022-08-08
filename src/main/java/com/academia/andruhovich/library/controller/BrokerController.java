package com.academia.andruhovich.library.controller;

import com.academia.andruhovich.library.model.Message;
import com.academia.andruhovich.library.service.BrokerService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_WRITE;

@RestController
@RequestMapping("/api/rabbitmq")
@RequiredArgsConstructor
public class BrokerController {

    private final AmqpTemplate template;
    private final BrokerService service;

    @PostMapping("/{message}")
    @PreAuthorize("hasAuthority('" + AUTHORITY_WRITE + "')")
    public Message getMessage(@PathVariable String message) {
        for (int i = 0; i < 10; i++) {
            template.convertAndSend("queue1", message);
        }
        return service.add(message);
    }

}
