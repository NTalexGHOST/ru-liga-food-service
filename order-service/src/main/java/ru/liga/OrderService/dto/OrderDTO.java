package ru.liga.OrderService.dto;

import io.micrometer.core.lang.Nullable;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.List;

enum OrderStatus {
    //  Есть ли смысл делать возможной отмену заказа (CANCELLED) пользователем
    //  или по нашему заданию этого не предусмотрено?
    CART, CANCELLED, REJECTED, PAID, PREPARING, COURIER, DELIVERED
}

@Data
@Accessors(chain = true)
public class OrderDTO {
    private long id;

    private CustomerDTO customer;

    private RestaurantDTO restaurant;

    private OrderStatus status;

    private @Nullable CourierDTO courier;

    private Timestamp timestamp;

    //  При подключении к БД можно класс сделать с аннотацией @Entity,
    //  а данный список пометить @ManyToOne и @JoinColumn для удобства
    private List<OrderItemDTO> items;
}
