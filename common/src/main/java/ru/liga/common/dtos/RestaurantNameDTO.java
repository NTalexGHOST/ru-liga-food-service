package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "DTO ресторана")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class RestaurantNameDTO {

    @Schema(description = "Название ресторана")
    @JsonProperty("name")
    private String name;
}
