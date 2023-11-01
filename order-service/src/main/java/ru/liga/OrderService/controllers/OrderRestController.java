package ru.liga.OrderService.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.liga.OrderService.services.OrderService;
import ru.liga.common.dtos.OrderDTO;
import ru.liga.common.dtos.RestaurantDTO;
import ru.liga.common.responses.AllOrdersResponse;

@Tag(name = "Контроллер для работы с заказами")
@RestController
@RequestMapping("order-service")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    @Operation(summary = "Возврат списка заказов")
    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public AllOrdersResponse getAllOrders() { return orderService.getAllOrders(); }

    @Operation(summary = "Возврат заказа по его id")
    @GetMapping("/order/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrderDTO getOrderById(@PathVariable("id") Long id) { return orderService.findOrderById(id); }

    /*@Operation(summary = "Создание заказа")
    @PostMapping("/")
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        orders.add(orderDTO);

        return orderDTO;
    }*/
}
