package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Schema(description = "DTO заказа для сервиса доставки")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DeliveryOrderDTO {

    @Schema(description = "Идентификатор")
    @JsonProperty("order_id")
    private UUID id;

    @Schema(description = "Ресторан")
    @JsonProperty("restaurant")
    private DeliveryRestaurantDTO restaurant;

    @Schema(description = "Покупатель")
    @JsonProperty("customer")
    private DeliveryCustomerDTO customer;

    @JsonIgnore
    private List<OrderItemDTO> items;

    @JsonIgnore
    private CourierDTO courier;

    @Schema(description = "Оплата курьеру")
    @JsonProperty("payment")
    private BigDecimal payment;
}
