package ru.liga.OrderService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.core.lang.Nullable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.liga.OrderService.dto.Statuses.OrderStatus;

import java.sql.Timestamp;
import java.util.List;

@Schema(description = "Сущность заказа")
@Data
@Accessors(chain = true)
public class OrderDTO {

    @Schema(description = "Идентификатор")
    private long id;

    @Schema(description = "Покупатель")
    @JsonIgnore
    private CustomerDTO customer;

    @Schema(description = "Ресторан")
    private RestaurantDTO restaurant;

    @Schema(description = "Статус заказа",
            allowableValues = { "CART", "CANCELLED", "REJECTED", "PAID", "PREPARING", "COURIER", "DELIVERED" })
    private OrderStatus status;

    @Schema(description = "Курьер")
    @JsonIgnore
    private @Nullable CourierDTO courier;

    @Schema(description = "Время заказа")
    private Timestamp timestamp;

    //  При подключении к БД в будущем так понимаю класс будет с аннотацией @Entity,
    //  тогда данный список можно будет пометить @ManyToOne и @JoinColumn для удобства
    @Schema(description = "Позиции в заказе")
    private List<OrderItemDTO> items;
}
