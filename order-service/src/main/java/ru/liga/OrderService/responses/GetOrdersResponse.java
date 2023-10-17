package ru.liga.OrderService.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.liga.OrderService.dto.OrderDTO;

import java.util.List;

@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class GetOrdersResponse {

    public GetOrdersResponse(List<OrderDTO> orders) {
        this.orders = orders;
    }

    @JsonProperty("orders")
    private List<OrderDTO> orders;

    @JsonProperty("page_index")
    private int pageIndex = 0;

    @JsonProperty("page_count")
    private int pageCount = 0;
}
