package ru.liga.orderservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.liga.orderservice.responses.CreateOrderResponse;
import ru.liga.orderservice.responses.CustomerOrdersResponse;
import ru.liga.orderservice.services.OrderService;
import ru.liga.common.dtos.ShortOrderDTO;
import ru.liga.common.dtos.FullOrderDTO;

@Tag(name = "Контроллер для работы с заказами")
@RestController @RequestMapping("order-service")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    @Operation(summary = "Возврат списка заказов")
    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public CustomerOrdersResponse getAllOrders() {

        return orderService.findAllOrders();
    }

    @Operation(summary = "Возврат заказа по его id")
    @GetMapping("/order/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FullOrderDTO getOrderById(@PathVariable("id") Long id) {

        return orderService.findOrderById(id);
    }

    @Operation(summary = "Создание заказа")
    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderResponse createOrder(@RequestBody ShortOrderDTO orderDTO) {

        return orderService.createOrder(orderDTO);
    }
}
