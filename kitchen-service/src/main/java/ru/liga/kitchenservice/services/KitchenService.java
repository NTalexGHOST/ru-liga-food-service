package ru.liga.kitchenservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ru.liga.common.dtos.OrderStatusDTO;
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
    private final ObjectMapper objectMapper;
    private final RabbitMQProducerServiceImpl rabbit;

    @SneakyThrows
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
        String message = objectMapper.writeValueAsString(new OrderStatusDTO(id, OrderStatus.KITCHEN_PREPARING));
        rabbit.sendMessage(message, "customers");

        String responseString = "Заказ [" + id + "] начали готовить";
        System.out.println(responseString); logger.info(responseString);

        return ResponseEntity.ok(responseString);
    }

    @SneakyThrows
    public ResponseEntity<String> declineOrder(UUID id) {

        //  Получение текущего статуса заказа
        ResponseEntity<String> response = getOrderStatus(id);
        if (!response.getStatusCode().is2xxSuccessful()) return response;
        OrderStatus orderStatus = OrderStatus.valueOf(response.getBody());

        //  Возможно принимать заказы рестораном только со статусом CUSTOMER_PAID
        if(!orderStatus.equals(OrderStatus.CUSTOMER_PAID))
            return new ResponseEntity<>("Заказ [" + id + "] нельзя принять", HttpStatus.INTERNAL_SERVER_ERROR);

        //  Установление заказу статуса KITCHEN_DENIED
        String message = objectMapper.writeValueAsString(new OrderStatusDTO(id, OrderStatus.KITCHEN_DENIED));
        rabbit.sendMessage(message, "customers");

        String responseString = "Заказ [" + id + "] успешно отклонен";
        System.out.println(responseString); logger.info(responseString);

        return ResponseEntity.ok(responseString);
    }

    @SneakyThrows
    public ResponseEntity<String> completeOrder(UUID id) {

        //  Получение текущего статуса заказа
        ResponseEntity<String> response = getOrderStatus(id);
        if (!response.getStatusCode().is2xxSuccessful()) return response;
        OrderStatus orderStatus = OrderStatus.valueOf(response.getBody());

        //  Возможно завершать заказы рестораном только со статусом KITCHEN_PREPARING
        if(!orderStatus.equals(OrderStatus.KITCHEN_PREPARING))
            return new ResponseEntity<>("Заказ [" + id + "] нельзя завершить", HttpStatus.INTERNAL_SERVER_ERROR);

        //  Установление заказу статуса DELIVERY_PENDING и оповещение сервиса доставки о новом заказе
        String message = objectMapper.writeValueAsString(new OrderStatusDTO(id, OrderStatus.DELIVERY_PENDING));
        rabbit.sendMessage(message, "customers");
        rabbit.sendMessage(id.toString(), "couriers");

        String responseString = "Заказ [" + id + "] успешно приготовлен";
        System.out.println(responseString); logger.info(responseString);

        return ResponseEntity.ok(responseString);
    }

    private ResponseEntity<String> getOrderStatus(UUID id) {

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
}
