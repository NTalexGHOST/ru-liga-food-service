package ru.liga.orderservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.liga.common.responses.CodeResponse;
import ru.liga.common.statuses.OrderStatus;
import ru.liga.orderservice.responses.ConfirmOrderResponse;
import ru.liga.orderservice.responses.CustomerOrdersResponse;
import ru.liga.orderservice.services.OrderService;
import ru.liga.common.dtos.FullOrderDTO;

@Tag(name = "Контроллер для работы с заказами")
@RestController @RequestMapping("order-service")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    @Operation(summary = "Возврат списка заказов")
    @GetMapping("/orders/all")
    @ResponseStatus(HttpStatus.OK)
    public CustomerOrdersResponse getAllOrders() {

        return orderService.getAllOrders();
    }

    @Operation(summary = "Возврат списка заказов покупателя")
    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public CustomerOrdersResponse getAllOrdersByCustomer() {

        return orderService.getAllOrdersByCustomer();
    }

    @Operation(summary = "Возврат заказа по его id")
    @GetMapping("/order/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FullOrderDTO getOrderById(@PathVariable("id") Long id) {

        return orderService.getOrderById(id);
    }

    @Operation(summary = "Смена статуса заказа")
    @PutMapping("/order/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CodeResponse changeOrderStatus(@PathVariable("id") Long id, @RequestParam("status") OrderStatus status) {

        return orderService.changeOrderStatus(id, status);
    }

    @Operation(summary = "Подтверждение заказа")
    @PutMapping("/order/{id}/confirm")
    @ResponseStatus(HttpStatus.OK)
    public ConfirmOrderResponse changeOrderStatus(@PathVariable("id") Long id) {

        return orderService.confirmOrder(id);
    }

    @Operation(summary = "Создание заказа")
    @PostMapping("/restaurant/{id}/order")
    @ResponseStatus(HttpStatus.CREATED)
    public CodeResponse createOrder(@PathVariable("id") Long id) {

        return orderService.createOrder(id);
    }
}
