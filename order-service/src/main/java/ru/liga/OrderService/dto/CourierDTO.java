package ru.liga.OrderService.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.awt.geom.Point2D;

enum CourierStatus {
    FREE, BUSY, REST
}

@Data
@Accessors(chain = true)
public class CourierDTO {
    private long id;

    private byte phone;

    private CourierStatus status;

    private Point2D.Double coordinates;
}
