package ru.liga.deliveryservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.liga.common.statuses.OrderStatus;
import ru.liga.deliveryservice.services.DeliveryService;
import ru.liga.common.responses.CodeResponse;
import ru.liga.common.responses.DeliveryOrdersResponse;

@Tag(name = "Контроллер сервиса доставки")
@RestController
@RequestMapping("delivery-service")
@RequiredArgsConstructor
public class DeliveryRestController {

    private final DeliveryService deliveryService;

    @Operation(summary = "Смена статуса заявки")
    @PostMapping("/delivery/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CodeResponse changeOrderStatus(@PathVariable("id") Long id, @RequestParam("status") OrderStatus status) {

        return deliveryService.changeOrderStatus(id, status);
    }

    @Operation(summary = "Получить информацию по доставке заказов")
    @PostMapping("/deliveries")
    @ResponseStatus(HttpStatus.OK)
    public DeliveryOrdersResponse getAllDeliveries(@RequestParam("status") OrderStatus status) {

        return deliveryService.findAllDeliveries(status);
    }
}
