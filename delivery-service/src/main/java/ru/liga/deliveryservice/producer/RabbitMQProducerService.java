package ru.liga.deliveryservice.producer;

public interface RabbitMQProducerService {

    void sendMessage(String message, String routingKey);
}
