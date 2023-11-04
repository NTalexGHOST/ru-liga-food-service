package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(description = "DTO заказа")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ShortOrderDTO {

    @Schema(description = "Идентификатор ресторана")
    @JsonProperty("restaurant_id")
    private long restaurantId;

    @Schema(description = "Позиции в заказе")
    @JsonProperty("menu_items")
    private List<ShortOrderItemDTO> items;
}
