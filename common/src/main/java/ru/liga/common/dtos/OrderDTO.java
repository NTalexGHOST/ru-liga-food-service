package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import ru.liga.common.statuses.OrderStatus;

import java.sql.Timestamp;
import java.util.List;

@Schema(description = "DTO заказа")
@Data
@Getter @Setter
@AllArgsConstructor
public class OrderDTO {

    @Schema(description = "Идентификатор")
    @JsonProperty("id")
    private long id;

    @Schema(description = "Покупатель")
    @JsonProperty("customer")
    private CustomerDTO customer;

    @Schema(description = "Ресторан")
    @JsonProperty("restaurant")
    private RestaurantDTO restaurant;

    @Schema(description = "Статус заказа")
    @JsonProperty("status")
    private OrderStatus status;

    @Schema(description = "Курьер")
    @JsonProperty("courier")
    private CourierDTO courier;

    @Schema(description = "Время заказа")
    @JsonProperty("timestamp")
    private Timestamp timestamp;

    @Schema(description = "Позиции в заказе")
    @JsonProperty("items")
    private List<OrderItemDTO> items;
}
