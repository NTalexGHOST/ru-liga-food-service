package ru.liga.deliveryservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QueueListener {

    private final DeliveryService deliveryService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "delivery-service")
    @SneakyThrows
    public void processMyQueue(String message) {

        System.out.println(message);
    }
}
