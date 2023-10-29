package ru.liga.common.entities;

import lombok.experimental.Accessors;
import ru.liga.common.statuses.CourierStatus;
import ru.liga.common.statuses.OrderStatus;

import javax.persistence.*;
import java.awt.geom.Point2D;
import java.sql.Timestamp;

@Entity(name = "customer_order")
@Accessors(chain = true)
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Courier courier;

    @Column(name = "timestamp")
    private Timestamp timestamp;
}
