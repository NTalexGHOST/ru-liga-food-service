package ru.liga.notificationservice.producer;

public interface RabbitMQProducerService {

    void sendMessage(String message, String routingKey);
}
