package ru.liga.orderservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import ru.liga.common.dtos.ShortOrderItemDTO;

import java.util.List;
import java.util.UUID;

@Schema(description = "DTO для создания заказа")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CreateOrderDTO {

    @Schema(description = "Идентификатор ресторана")
    @JsonProperty("restaurant_id")
    private UUID restaurantId;

    @Schema(description = "Позиции в заказе")
    @JsonProperty("menu_items")
    private List<ShortOrderItemDTO> items;
}
