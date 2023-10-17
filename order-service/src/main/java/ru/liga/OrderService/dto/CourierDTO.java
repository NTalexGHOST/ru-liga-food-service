package ru.liga.OrderService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.liga.OrderService.dto.Statuses.CourierStatus;

import java.awt.geom.Point2D;

@Schema(description = "Сущность курьера")
@Data
@Accessors(chain = true)
public class CourierDTO {

    @Schema(description = "Идентификатор")
    private long id;

    @Schema(description = "Номер телефона")
    private byte phone;

    @Schema(description = "Статус курьера", allowableValues = { "FREE", "BUSY", "REST" })
    private CourierStatus status;

    @Schema(description = "Координаты курьера в Point2D.Double")
    private Point2D.Double coordinates;
}
