package ru.liga.kitchenservice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Schema(description = "DTO цены позиции в ресторане")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MenuItemPriceDTO {

    @Schema(description = "Цена позиции")
    @JsonProperty("price")
    private BigDecimal price;
}
