package ru.liga.OrderService.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RestaurantDTO {
    private long id;

    private String name;

    private String address;

    private ru.liga.OrderService.dto.RestaurantStatus status;
}
