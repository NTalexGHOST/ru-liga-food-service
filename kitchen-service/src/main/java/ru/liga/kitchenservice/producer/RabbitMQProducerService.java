package ru.liga.kitchenservice.producer;

public interface RabbitMQProducerService {

    void sendMessage(String message, String routingKey);
}
