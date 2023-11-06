package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import ru.liga.common.statuses.OrderStatus;

@Schema(description = "DTO для смены статуса заказа")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class OrderStatusDTO {

    @Schema(description = "Статус заказа", allowableValues = { "DELIVERY_PICKING", "DELIVERY_COMPLETE",
            "DELIVERY_DENIED", "DELIVERY_PENDING", "DELIVERY_DELIVERING", "DELIVERY_REFUNDED", "CUSTOMER_PAID",
            "CUSTOMER_CANCELLED", "KITCHEN_ACCEPTED", "KITCHEN_DENIED", "KITCHEN_REFUNDED", "KITCHEN_PREPARING"})
    @JsonProperty("order_action")
    private OrderStatus status;
}
