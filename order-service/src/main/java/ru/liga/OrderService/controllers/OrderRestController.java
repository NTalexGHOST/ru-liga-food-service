package ru.liga.OrderService.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.liga.OrderService.dto.OrderDTO;
import ru.liga.OrderService.dto.OrderStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class OrderRestController {

    //  Заглушка для хранения данных
    HashMap<Long, OrderDTO> orders = new HashMap<>();

    @GetMapping("/orders")
    public HashMap<Long, OrderDTO> getOrders() {
        if (orders.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return orders;
    }

    //  Поиски по id и тому же статусу можно будет позже реализовать уже через @Repository
    @GetMapping("/order/{id}")
    public OrderDTO getOrderById(@PathVariable("id") Long id) {
        if (orders.get(id) == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return orders.get(id);
    }

    @PostMapping("/order")
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        orders.put(orderDTO.getId(), orderDTO);

        return orderDTO;
    }
}
