package ru.liga.kitchenservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.liga.kitchenservice.services.KitchenService;
import ru.liga.common.dtos.OrderStatusDTO;
import ru.liga.common.responses.RestaurantOrdersResponse;

@Tag(name = "Контроллер сервиса доставки")
@RestController
@RequestMapping("kitchen-service")
@RequiredArgsConstructor
public class KitchenRestController {

    private final KitchenService kitchenService;

    //  URL /orders?status=active/complete/denied решено было изменить на /orders с передачей OrderStatusDTO
    @Operation(summary = "Получение всех заказов ресторана с соответствующим статусом")
    @PostMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantOrdersResponse getAllOrders(@RequestBody OrderStatusDTO statusDTO) {

        return kitchenService.findAllOrders(statusDTO);
    }
}
