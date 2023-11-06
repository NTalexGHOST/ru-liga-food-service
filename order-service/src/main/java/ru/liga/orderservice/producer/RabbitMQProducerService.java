package ru.liga.orderservice.producer;

public interface RabbitMQProducerService {

    void sendMessage(String message, String routingKey);
}
