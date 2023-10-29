package ru.liga.common.entities;

import lombok.experimental.Accessors;
import ru.liga.common.statuses.CourierStatus;

import javax.persistence.*;
import java.awt.geom.Point2D;

@Entity(name = "customer")
@Accessors(chain = true)
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private Point2D.Double address;
}
