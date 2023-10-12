package ru.liga.OrderService.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderItemDTO {
    private long id;

    private OrderDTO order;

    private MenuItemDTO menuItem;

    private float price;

    private byte quantity;
}
