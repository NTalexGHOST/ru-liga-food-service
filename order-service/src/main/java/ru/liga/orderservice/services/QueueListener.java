package ru.liga.orderservice.services;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.liga.orderservice.producer.RabbitMQProducerServiceImpl;


@Service
@RequiredArgsConstructor
public class QueueListener {

    private final RabbitMQProducerServiceImpl rabbit;

    @SneakyThrows
    @RabbitListener(queues = "customers")
    public void processMyQueue(String message) {

        System.out.println(message);
    }
}
