package ru.liga.common.entities;

import lombok.experimental.Accessors;
import ru.liga.common.statuses.CourierStatus;
import ru.liga.common.statuses.RestaurantStatus;

import javax.persistence.*;
import java.awt.geom.Point2D;

@Entity(name = "restaurant")
@Accessors(chain = true)
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private Point2D.Double address;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RestaurantStatus status;
}
