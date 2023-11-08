package ru.liga.deliveryservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.liga.common.statuses.CourierStatus;
import ru.liga.common.statuses.OrderStatus;
import ru.liga.deliveryservice.services.DeliveryService;
import ru.liga.common.responses.CodeResponse;
import ru.liga.common.responses.DeliveryOrdersResponse;

import java.util.UUID;

@Tag(name = "Контроллер сервиса доставки")
@RestController
@RequestMapping("delivery-service")
@RequiredArgsConstructor
public class DeliveryRestController {

    private final DeliveryService deliveryService;

    @Operation(summary = "Получить информацию по доставке заказов")
    @GetMapping("/deliveries")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DeliveryOrdersResponse> getAllDeliveries() {

        return deliveryService.findAllDeliveries();
    }

    @Operation(summary = "Принять доставку заказа")
    @PutMapping("/delivery/{orderId}/take")
    public ResponseEntity<String> takeOrder(@PathVariable("orderId") UUID orderId) {

        return deliveryService.takeOrder(orderId);
    }

    @Operation(summary = "Завершить доставку заказа")
    @PutMapping("/delivery/{orderId}/complete")
    public ResponseEntity<String> completeOrder(@PathVariable("orderId") UUID orderId) {

        return deliveryService.completeOrder(orderId);
    }
}
