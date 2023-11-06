package ru.liga.notificationservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import ru.liga.notificationservice.producer.RabbitMQProducerServiceImpl;

@Service
@RequiredArgsConstructor
public class QueueListener {

    private final RabbitMQProducerServiceImpl rabbit;

    @RabbitListener(queues = "customers")
    public void processCustomersQueue(String message) {

        rabbit.sendMessage(message, "order-service");
    }

    @RabbitListener(queues = "restaurants")
    public void processRestaurantsQueue(String message) {

        rabbit.sendMessage(message, "kitchen-service");
    }

    @RabbitListener(queues = "couriers")
    public void processCouriersQueue(String message) {

        rabbit.sendMessage(message, "delivery-service");
    }
}
