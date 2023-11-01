package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import ru.liga.common.statuses.RestaurantStatus;

import java.awt.geom.Point2D;

@Schema(description = "DTO ресторана")
@Data
@Accessors(chain = true)
public class RestaurantDTO {

    @Schema(description = "Идентификатор")
    @JsonProperty("id")
    private long id;

    @Schema(description = "Название ресторана")
    @JsonProperty("name")
    private String name;

    @Schema(description = "Координаты")
    @JsonProperty("address")
    private Point2D.Double address;

    @Schema(description = "Статус ресторана", allowableValues = { "CLOSED", "OPEN" })
    @JsonProperty("status")
    private RestaurantStatus status;
}
