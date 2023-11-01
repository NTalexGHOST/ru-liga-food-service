package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Schema(description = "DTO позиции в заказе")
@Data
@Getter @Setter
@AllArgsConstructor
public class OrderItemDTO {

    @Schema(description = "Идентификатор")
    @JsonProperty("id")
    private long id;

    @Schema(description = "Заказ")
    @JsonProperty("order")
    private OrderDTO order;

    @Schema(description = "Позиция в ресторане")
    @JsonProperty("menuItem")
    private MenuItemDTO menuItem;

    @Schema(description = "Цена в момент заказа")
    @JsonProperty("price")
    private BigDecimal price;

    @Schema(description = "Количество")
    @JsonProperty("quantity")
    private byte quantity;
}
