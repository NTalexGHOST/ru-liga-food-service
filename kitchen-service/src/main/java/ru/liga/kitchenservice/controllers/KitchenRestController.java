package ru.liga.kitchenservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.liga.common.responses.CodeResponse;
import ru.liga.common.statuses.OrderStatus;
import ru.liga.kitchenservice.services.KitchenService;
import ru.liga.common.responses.RestaurantOrdersResponse;

@Tag(name = "Контроллер для работы с рестораном")
@RestController
@RequestMapping("kitchen-service")
@RequiredArgsConstructor
public class KitchenRestController {

    private final KitchenService kitchenService;

    @Operation(summary = "Получение всех заказов ресторана с соответствующим статусом")
    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantOrdersResponse getAllOrders(@RequestParam("status") OrderStatus status) {

        return kitchenService.findAllOrders(status);
    }

    @Operation(summary = "Смена статуса заказа")
    @PostMapping("/order/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CodeResponse changeOrderStatus(@PathVariable("id") Long id, @RequestParam("status") OrderStatus status) {

        return kitchenService.changeOrderStatus(id, status);
    }
}
