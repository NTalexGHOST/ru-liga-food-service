package ru.liga.OrderService.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.awt.geom.Point2D;

@Data
@Accessors(chain = true)
public class CourierDTO {
    private long id;

    private byte phone;

    private ru.liga.OrderService.dto.CourierStatus status;

    private Point2D.Double coordinates;
}
