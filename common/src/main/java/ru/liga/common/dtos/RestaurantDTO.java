package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import ru.liga.common.statuses.RestaurantStatus;

@Schema(description = "DTO ресторана")
@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDTO {

    @Schema(description = "Идентификатор")
    @JsonProperty("id")
    private long id;

    @Schema(description = "Название ресторана")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Координаты")
    @JsonProperty("address")
    private String address;

    @Schema(description = "Статус ресторана", allowableValues = { "CLOSED", "OPEN" })
    @JsonProperty("status")
    private RestaurantStatus status;
}
