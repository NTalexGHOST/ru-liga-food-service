package ru.liga.orderservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.liga.common.dtos.FullMenuItemDTO;
import ru.liga.common.dtos.FullOrderItemDTO;
import ru.liga.common.responses.CodeResponse;
import ru.liga.common.responses.OrderItemsResponse;
import ru.liga.common.responses.RestaurantMenuResponse;
import ru.liga.orderservice.services.OrderItemService;

@Tag(name = "Контроллер для работы с позициями заказа")
@RestController @RequestMapping("order-service")
@RequiredArgsConstructor
public class OrderItemRestController {

    private final OrderItemService orderItemService;

    @Operation(summary = "Полное меню ресторана")
    @GetMapping("/order/{id}/order-items")
    @ResponseStatus(HttpStatus.OK)
    public OrderItemsResponse getOrderItemsByOrderId(@PathVariable("id") Long id) {

        return orderItemService.getOrderItemsByOrderId(id);
    }

    @Operation(summary = "Получение позиции заказа")
    @GetMapping("/order-item/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FullOrderItemDTO getOrderItemById(@PathVariable("id") Long id) {

        return orderItemService.getOrderItemById(id);
    }

    @Operation(summary = "Создание позиции меню")
    @PostMapping("/order-item")
    @ResponseStatus(HttpStatus.OK)
    public CodeResponse createOrderItem(@RequestBody FullMenuItemDTO menuItemDTO) {

        return orderItemService.createOrderItem(menuItemDTO);
    }

    @Operation(summary = "Удаление позиции меню")
    @DeleteMapping("/order-item/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CodeResponse deleteOrderItem(@PathVariable("id") Long id) {

        return orderItemService.deleteOrderItem(id);
    }
}
