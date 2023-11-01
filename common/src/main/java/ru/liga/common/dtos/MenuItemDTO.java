package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Schema(description = "DTO позиции в ресторане")
@Data
@Getter @Setter
@AllArgsConstructor
public class MenuItemDTO {

    @Schema(description = "Идентификатор")
    @JsonProperty("id")
    private long id;

    @Schema(description = "Ресторан")
    @JsonProperty("restaurant")
    private RestaurantDTO restaurant;

    @Schema(description = "Название ресторана")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Цена позиции")
    @JsonProperty("price")
    private BigDecimal price;

    @Schema(description = "Путь до фотографии")
    @JsonProperty("image")
    private String image;

    @Schema(description = "Описание позиции")
    @JsonProperty("description")
    private String description;
}
