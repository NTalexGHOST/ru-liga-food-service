package ru.liga.OrderService.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;
import ru.liga.OrderService.dto.OrderDTO;
import ru.liga.OrderService.dto.OrderItemDTO;
import ru.liga.OrderService.dto.RestaurantDTO;

import java.sql.Timestamp;
import java.util.List;

@Accessors(chain = true)
@AllArgsConstructor
public class GetOrderByIdResponse {

    public GetOrderByIdResponse(OrderDTO order) {
        this.id = order.getId();
        this.restaurant = order.getRestaurant();
        this.timestamp = order.getTimestamp();
        this.items = order.getItems();
    }

    @JsonProperty("id")
    private long id;

    @JsonProperty("restaurant")
    private RestaurantDTO restaurant;

    @JsonProperty("timestamp")
    private Timestamp timestamp;

    @JsonProperty("items")
    private List<OrderItemDTO> items;
}
