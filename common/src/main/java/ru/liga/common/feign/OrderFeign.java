package ru.liga.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

/**
 * Feign-интерфейс, отвечающий за отправление запросов к сервису заказа
 */
@FeignClient(name = "order-service", url = "http://localhost:8084")
public interface OrderFeign {

    /**
     * Метод отвечает за получение статуса заказа
     * @param id - идентификатор заказа
     * @return возвращает код ответа с помощью {@link ResponseEntity}
     */
    @GetMapping("/order/{id}/status")
    ResponseEntity<String> getOrderStatus(@PathVariable("id") UUID id);
}
