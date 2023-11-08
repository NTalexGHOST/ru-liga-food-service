package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.liga.common.statuses.OrderStatus;

import java.util.UUID;

@Schema(description = "DTO для смены статуса заказа")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderStatusDTO {

    @Schema(description = "Идентификатор")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "Статус")
    @JsonProperty("status")
    private OrderStatus status;
}
