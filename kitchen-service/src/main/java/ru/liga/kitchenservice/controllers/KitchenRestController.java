package ru.liga.kitchenservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.liga.common.responses.CodeResponse;
import ru.liga.common.statuses.OrderStatus;
import ru.liga.kitchenservice.services.KitchenService;
import ru.liga.common.responses.RestaurantOrdersResponse;

import java.util.UUID;

@Tag(name = "Контроллер для работы с рестораном")
@RestController
@RequestMapping("kitchen-service")
@RequiredArgsConstructor
public class KitchenRestController {

    private final KitchenService kitchenService;

    @Operation(summary = "Принять заказ")
    @PostMapping("/kitchen/{orderId}/accept")
    public ResponseEntity<String> acceptOrder(@PathVariable("orderId") UUID orderId) {

        return kitchenService.acceptOrder(orderId);
    }
}
