package ru.liga.OrderService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Base64;

@Schema(description = "Сущность позиции в ресторане")
@Data
@Accessors(chain = true)
public class MenuItemDTO {

    @Schema(description = "Идентификатор")
    private long id;

    @Schema(description = "Ресторан")
    private RestaurantDTO restaurant;

    @Schema(description = "Название ресторана")
    private String name;

    @Schema(description = "Цена позиции")
    private float price;

    @Schema(description = "Фотография позиции в Base64")
    private Base64 image;

    @Schema(description = "Описание позиции")
    private String description;
}
