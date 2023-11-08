package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import ru.liga.common.entities.Restaurant;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Schema(description = "DTO заказа")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class FullOrderDTO {

    @Schema(description = "Идентификатор")
    @JsonProperty("id")
    private UUID id;

    @JsonIgnore
    private Restaurant restaurant;
    @Schema(description = "Ресторан")
    @JsonProperty("restaurant")
    private String restaurantName;

    @Schema(description = "Время заказа")
    @JsonProperty("timestamp")
    private Timestamp timestamp;

    @Schema(description = "Позиции в заказе")
    @JsonProperty("items")
    private List<OrderItemDTO> items;
}
