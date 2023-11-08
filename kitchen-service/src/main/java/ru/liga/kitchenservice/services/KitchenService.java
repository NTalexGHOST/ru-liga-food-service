package ru.liga.kitchenservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ru.liga.common.feign.OrderFeign;
import ru.liga.common.statuses.OrderStatus;
import ru.liga.kitchenservice.producer.RabbitMQProducerServiceImpl;

import java.util.UUID;

@Service @Slf4j
@RequiredArgsConstructor
@ComponentScan(basePackages = "ru.liga.common")
public class KitchenService {

    private final Logger logger = LoggerFactory.getLogger(KitchenService.class);

    private final OrderFeign orderFeign;
    private final RabbitMQProducerServiceImpl rabbit;

    public ResponseEntity<String> getOrderStatus(UUID id) {

        //  Получение текущего статуса заказа
        ResponseEntity<String> response = orderFeign.getOrderStatus(id);
        switch (response.getStatusCode()) {
            case OK:
                break;
            case NOT_FOUND:
                return new ResponseEntity<>("Заказ [" + id + "] не найден", HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity<>("Не удалось получить статус заказа [" + id + "]", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    public ResponseEntity<String> acceptOrder(UUID id) {

        //  Получение текущего статуса заказа
        ResponseEntity<String> response = getOrderStatus(id);
        if (!response.getStatusCode().is2xxSuccessful()) return response;
        OrderStatus orderStatus = OrderStatus.valueOf(response.getBody());

        //  Возможно отклонить заказы рестораном только со статусом CUSTOMER_PAID
        if(!orderStatus.equals(OrderStatus.CUSTOMER_PAID))
            return new ResponseEntity<>("Заказ [" + id + "] нельзя принять", HttpStatus.INTERNAL_SERVER_ERROR);

        //  Установление заказу статуса KITCHEN_PREPARING
        //  Еще существует статус KITCHEN_ACCEPTED, но думаю он излишен и по своей сути дублирует KITCHEN_PREPARING
        response = orderFeign.changeOrderStatus(id, OrderStatus.KITCHEN_PREPARING);

        return response;
    }

    public ResponseEntity<String> declineOrder(UUID id) {

        //  Получение текущего статуса заказа
        ResponseEntity<String> response = getOrderStatus(id);
        if (!response.getStatusCode().is2xxSuccessful()) return response;
        OrderStatus orderStatus = OrderStatus.valueOf(response.getBody());

        //  Возможно принимать заказы рестораном только со статусом CUSTOMER_PAID
        if(!orderStatus.equals(OrderStatus.CUSTOMER_PAID))
            return new ResponseEntity<>("Заказ [" + id + "] нельзя принять", HttpStatus.INTERNAL_SERVER_ERROR);

        //  Установление заказу статуса KITCHEN_DENIED и его последующее уведомление об этом
        response = orderFeign.changeOrderStatus(id, OrderStatus.KITCHEN_DENIED);
        rabbit.sendMessage("Заказ [" + id + "] был отменен рестораном", "customers");

        return response;
    }
}
