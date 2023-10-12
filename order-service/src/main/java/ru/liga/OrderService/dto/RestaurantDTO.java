package ru.liga.OrderService.dto;

import lombok.Data;
import lombok.experimental.Accessors;

enum RestaurantStatus {
    CLOSED, OPEN, OVERFLOWED
}

@Data
@Accessors(chain = true)
public class RestaurantDTO {
    private long id;

    private String name;

    private String address;

    private RestaurantStatus status;
}
