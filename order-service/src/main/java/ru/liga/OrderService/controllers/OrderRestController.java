package ru.liga.OrderService.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.OrderService.dto.OrderDTO;
import ru.liga.OrderService.responses.GetOrderByIdResponse;
import ru.liga.OrderService.responses.GetOrdersResponse;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "Контроллер для работы с заказами")
@RestController
public class OrderRestController {

    //  Заглушка для хранения данных
    List<OrderDTO> orders = new ArrayList<>();

    @Operation(summary = "Возврат списка заказов")
    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GetOrdersResponse> getOrders() {
        GetOrdersResponse response;

        try {
            response = new GetOrdersResponse(orders);
        } catch (Exception e) {
            response = new GetOrdersResponse();

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
        Поиски по id и тому же статусу можно будет позже реализовать уже через @CrudRepository
        просто чтоб не выдумывать здесь велосипеды))

        Если я верно понял, то будет вообще наследование PagingAndSortingRepository или полный JpaRepository
        поскольку в примере запросов есть некоторые намеки на это в виде переменных page_index и page_count
    */
    @Operation(summary = "Возврат заказа по его id")
    @GetMapping("/order/{id}")
    //  Не совсем уверен насколько верно использовать неопределенный ResponseEntity
    public ResponseEntity getOrderById(@PathVariable("id") Long id) {
        GetOrderByIdResponse response;

        try {
            //  Допустим здесь происходит поиск по id в репозитории
            OrderDTO order = new OrderDTO();
            //  При этом репозиторий возвращает Optional и выполняем проверку на Null (isPresent)
            if (order == null) return new ResponseEntity<>("404 Not Found", HttpStatus.NOT_FOUND);

            response = new GetOrderByIdResponse(order);
        } catch (Exception e) {
            return new ResponseEntity<>("500 Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Создание заказа")
    @PostMapping("/order")
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        orders.add(orderDTO);

        return orderDTO;
    }
}
