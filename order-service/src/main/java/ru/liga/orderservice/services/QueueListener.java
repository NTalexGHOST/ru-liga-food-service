package ru.liga.orderservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import ru.liga.common.dtos.OrderStatusDTO;


@Service
@RequiredArgsConstructor
public class QueueListener {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @RabbitListener(queues = "order-service")
    public void processMyQueue(String message) {

        OrderStatusDTO orderDTO = objectMapper.readValue(message, OrderStatusDTO.class);
        orderService.changeOrderStatus(orderDTO.getId(), orderDTO.getStatus());
        System.out.println("Получил");
    }
}
