package com.academia.andruhovich.library.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class RabbitMqListener {

    @RabbitListener(queues = "queue1")
    public void process1Queue1(String message) {
        System.out.println("Received to listener 1 from queue 1: " + message);
    }

    @RabbitListener(queues = "queue1")
    public void process2Queue1(String message) {
        System.out.println("Received to listener 2 from queue 1: " + message);
    }

}
