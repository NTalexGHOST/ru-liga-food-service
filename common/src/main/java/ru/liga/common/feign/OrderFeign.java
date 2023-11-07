package ru.liga.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.liga.common.responses.CodeResponse;
import ru.liga.common.statuses.OrderStatus;

/**
 * Feign-интерфейс, отвечающий за отправление запросов к сервису заказа
 */
@FeignClient(name = "order-service", url = "http://localhost:8084")
public interface OrderFeign {

    /**
     * Метод отвечает за возможность изменения статуса заказа
     * @param id - идентификатор заказа
     * @param status - новый статус заказа
     * @return возвращает код ответа с описанием {@link CodeResponse}
     */
    @PutMapping("/order/{id}")
    CodeResponse changeOrderStatus(@PathVariable("id") Long id, @RequestParam("status") OrderStatus status);
}
