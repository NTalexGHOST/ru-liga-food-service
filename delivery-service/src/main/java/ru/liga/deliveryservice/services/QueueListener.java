package ru.liga.deliveryservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QueueListener {

    private final DeliveryService deliveryService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "delivery-service")
    @SneakyThrows
    public void processMyQueue(String message) {

        UUID id = UUID.fromString(message);
        deliveryService.findCourierForOrder(id);
    }
}
