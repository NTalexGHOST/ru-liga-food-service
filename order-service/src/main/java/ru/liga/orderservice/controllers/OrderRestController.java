package ru.liga.orderservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ru.liga.common.responses.CodeResponse;
import ru.liga.common.statuses.OrderStatus;
import ru.liga.orderservice.dtos.CreateOrderDTO;
import ru.liga.orderservice.responses.CreateOrderResponse;
import ru.liga.orderservice.responses.CustomerOrdersResponse;
import ru.liga.orderservice.services.OrderService;
import ru.liga.common.dtos.FullOrderDTO;

import java.util.UUID;

@Tag(name = "Контроллер для работы с заказами")
@RestController @RequestMapping("order-service")
@RequiredArgsConstructor
public class OrderRestController {

    private final OrderService orderService;

    @Operation(summary = "Вернуть список заказов покупателя")
    @GetMapping("/orders")
    public ResponseEntity<CustomerOrdersResponse> getAllOrdersByCustomer() {

        return orderService.getAllOrdersByCustomer();
    }

    @Operation(summary = "Вернуть заказ по его id")
    @GetMapping("/order/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FullOrderDTO getOrderById(@PathVariable("id") UUID id) {

        return orderService.getOrderById(id);
    }

    @Operation(summary = "Изменить статус заказа")
    @PutMapping("/order/{id}/status")
    public ResponseEntity<String> changeOrderStatus(@PathVariable("id") UUID orderId,
                                                    @RequestParam("status") OrderStatus orderStatus) {

        return orderService.changeOrderStatus(orderId, orderStatus);
    }

    @Operation(summary = "Изменить статус заказа")
    @GetMapping("/order/{id}/status")
    public ResponseEntity<String> getOrderStatus(@PathVariable("id") UUID orderId) {

        return orderService.getOrderStatus(orderId);
    }
    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateOrderResponse createOrder(CreateOrderDTO orderDTO) {

        return orderService.createOrder(orderDTO);
    }
}
