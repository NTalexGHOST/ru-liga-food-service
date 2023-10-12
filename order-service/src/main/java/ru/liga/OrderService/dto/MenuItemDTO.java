package ru.liga.OrderService.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Base64;

@Data
@Accessors(chain = true)
public class MenuItemDTO {
    private long id;

    private RestaurantDTO restaurant;

    private String name;

    private float price;

    private Base64 image;

    private String description;
}
