package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Schema(description = "DTO заказа конкретного ресторана")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RestaurantOrderDTO {

    @Schema(description = "Идентификатор заказа")
    @JsonProperty("id")
    private long id;

    @Schema(description = "Позиции в заказе")
    @JsonProperty("menu_items")
    private List<ShortOrderItemDTO> items;
}
