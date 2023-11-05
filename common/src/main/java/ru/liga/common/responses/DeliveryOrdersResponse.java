package ru.liga.common.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import ru.liga.common.dtos.DeliveryOrderDTO;

import java.util.List;

@Schema(description = "DTO для вывода доставок")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DeliveryOrdersResponse {

    @Schema(description = "Коллекция заказов")
    @JsonProperty("deliveries")
    private List<DeliveryOrderDTO> orders = null;

    @JsonProperty("page_index")
    private int pageIndex = 0;

    @JsonProperty("page_count")
    private int pageCount = 0;
}
