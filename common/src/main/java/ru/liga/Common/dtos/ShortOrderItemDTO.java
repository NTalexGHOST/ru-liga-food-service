package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.UUID;

@Schema(description = "DTO позиции при создании заказа")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ShortOrderItemDTO {

    @Schema(description = "Идентификатор позиции в меню")
    @JsonProperty("menu_item_id")
    private UUID menuItemId;

    @Schema(description = "Количество")
    @JsonProperty("quantity")
    private byte quantity;
}
