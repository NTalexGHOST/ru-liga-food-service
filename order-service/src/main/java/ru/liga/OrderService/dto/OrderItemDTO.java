package ru.liga.OrderService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Сущность позиции в заказе")
@Data
@Accessors(chain = true)
public class OrderItemDTO {

    @Schema(description = "Идентификатор")
    @JsonIgnore
    private long id;

    @Schema(description = "Заказ")
    @JsonIgnore
    private OrderDTO order;

    @Schema(description = "Позиция в ресторане")
    private MenuItemDTO menuItem;

    //  При этом, я так понимаю, здесь цена выставляется уже с учетом например скидок и т.д.
    @Schema(description = "Цена в момент заказа")
    private float price;

    @Schema(description = "Количество")
    private byte quantity;
}
