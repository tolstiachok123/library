package com.academia.andruhovich.library.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.academia.andruhovich.library.security.SecurityAuthorities.AUTHORITY_READ;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/rabbitmq")
@RequiredArgsConstructor
public class SampleController {

    private final AmqpTemplate template;

    @PostMapping("/{message}")
    @PreAuthorize("hasAuthority('" + AUTHORITY_READ + "')")
    public ResponseEntity<String> postMessage(@PathVariable String message) {
        for (int i = 0; i < 10; i++) {
            template.convertAndSend("queue1", message);
        }
        return ResponseEntity.status(OK).build();
    }

}
