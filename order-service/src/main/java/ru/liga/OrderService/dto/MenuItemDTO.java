package ru.liga.OrderService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Base64;

@Schema(description = "Сущность позиции в ресторане")
@Data
@Accessors(chain = true)
public class MenuItemDTO {

    @Schema(description = "Идентификатор")
    @JsonIgnore
    private long id;

    @Schema(description = "Ресторан")
    @JsonIgnore
    private RestaurantDTO restaurant;

    @Schema(description = "Название ресторана")
    @JsonIgnore
    private String name;

    @Schema(description = "Цена позиции")
    @JsonIgnore
    private BigDecimal price;

    @Schema(description = "Фотография позиции в Base64")
    private String image;

    @Schema(description = "Описание позиции")
    private String description;
}
