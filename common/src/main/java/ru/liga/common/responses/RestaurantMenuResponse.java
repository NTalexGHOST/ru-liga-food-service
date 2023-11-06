package ru.liga.common.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import ru.liga.common.dtos.FullMenuItemDTO;

import java.util.List;

@Schema(description = "DTO для вывода меню ресторана")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RestaurantMenuResponse {

    @Schema(description = "Коллекция позиций")
    @JsonProperty("menu_items")
    private List<FullMenuItemDTO> items = null;

    @JsonProperty("page_index")
    private int pageIndex = 0;

    @JsonProperty("page_count")
    private int pageCount = 0;
}
