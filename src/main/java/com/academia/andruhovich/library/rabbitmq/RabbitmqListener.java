package com.academia.andruhovich.library.rabbitmq;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class RabbitmqListener {

    @RabbitListener(queues = "queue")
    public void processQueueByFirstListener(String message) {
        System.out.println("Received to the first listener from queue: " + message);
    }

    @RabbitListener(queues = "queue")
    public void processQueueBySecondListener(String message) {
        System.out.println("Received to the second listener from queue: " + message);
    }

}
