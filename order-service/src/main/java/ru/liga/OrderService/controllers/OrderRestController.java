package ru.liga.OrderService.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.liga.OrderService.dto.OrderDTO;

import java.util.HashMap;
import java.util.List;

@Tag(name = "Контроллер для работы с заказами")
@RestController
public class OrderRestController {

    //  Заглушка для хранения данных
    HashMap<Long, OrderDTO> orders = new HashMap<>();

    @Operation(summary = "Возврат списка заказов")
    @GetMapping("/orders")
    public List<OrderDTO> getOrders() {
        if (orders.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return (List<OrderDTO>) orders.values();
    }

    /*
        Поиски по id и тому же статусу можно будет позже реализовать уже через @CrudRepository
        просто чтоб не выдумывать здесь велосипеды))

        Если я верно понял, то будет вообще наследование PagingAndSortingRepository или полный JpaRepository
        поскольку в примере запросов есть некоторые намеки на это в виде переменных page_index и page_count
    */
    @Operation(summary = "Возврат заказа по его id")
    @GetMapping("/order/{id}")
    public OrderDTO getOrderById(@PathVariable("id") Long id) {
        if (orders.get(id) == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return orders.get(id);
    }

    @Operation(summary = "Создание заказа")
    @PostMapping("/order")
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        orders.put(orderDTO.getId(), orderDTO);

        return orderDTO;
    }
}
