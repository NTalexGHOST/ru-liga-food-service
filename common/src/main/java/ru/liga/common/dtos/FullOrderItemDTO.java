package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Schema(description = "DTO позиции в заказе")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class FullOrderItemDTO {

    @JsonIgnore
    private MenuItemDTO menuItem;

    @Schema(description = "Название позиции")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Цена в момент заказа")
    @JsonProperty("price")
    private BigDecimal price;

    @Schema(description = "Количество")
    @JsonProperty("quantity")
    private byte quantity;

    @Schema(description = "Описание позиции")
    @JsonProperty("description")
    private String description;

    @Schema(description = "Путь до фотографии")
    @JsonProperty("image")
    private String image;
}
