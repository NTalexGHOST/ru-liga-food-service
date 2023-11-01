package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import ru.liga.common.statuses.CourierStatus;

import java.awt.geom.Point2D;

@Schema(description = "DTO курьера")
@Data
@Accessors(chain = true)
public class CourierDTO {

    @Schema(description = "Идентификатор")
    @JsonProperty("id")
    private long id;

    @Schema(description = "Номер телефона")
    @JsonProperty("phone")
    private String phone;

    @Schema(description = "Статус курьера", allowableValues = { "FREE", "BUSY", "REST" })
    private CourierStatus status;

    @Schema(description = "Координаты")
    private Point2D.Double coordinates;
}
