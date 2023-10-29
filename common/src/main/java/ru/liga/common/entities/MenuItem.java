package ru.liga.common.entities;

import lombok.experimental.Accessors;
import ru.liga.common.statuses.CourierStatus;

import javax.persistence.*;
import java.awt.geom.Point2D;
import java.math.BigDecimal;

@Entity(name = "menu_item")
@Accessors(chain = true)
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;
}
