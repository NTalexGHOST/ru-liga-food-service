package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Schema(description = "DTO заказа")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderDTO {

    @Schema(description = "Идентификатор")
    @JsonProperty("id")
    private long id;

    @Schema(description = "Ресторан")
    @JsonProperty("restaurant")
    private RestaurantNameDTO restaurant;

    @Schema(description = "Время заказа")
    @JsonProperty("timestamp")
    private Timestamp timestamp;

    @Schema(description = "Позиции в заказе")
    @JsonProperty("items")
    private List<OrderItemDTO> items;
}
