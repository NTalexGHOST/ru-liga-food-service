package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;

@Schema(description = "DTO позиции в ресторане")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MenuItemDTO {

    @Schema(description = "Путь до фотографии")
    @JsonProperty("image")
    private String image;

    @Schema(description = "Описание позиции")
    @JsonProperty("description")
    private String description;
}
