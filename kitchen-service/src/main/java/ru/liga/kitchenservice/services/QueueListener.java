package ru.liga.kitchenservice.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueListener {

    @SneakyThrows
    @RabbitListener(queues = "kitchen-service")
    public void processMyQueue(String message) {

        System.out.println(message);
    }
}
