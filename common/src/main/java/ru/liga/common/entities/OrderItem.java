package ru.liga.common.entities;

import lombok.experimental.Accessors;
import ru.liga.common.statuses.CourierStatus;

import javax.persistence.*;
import java.awt.geom.Point2D;
import java.math.BigDecimal;

@Entity(name = "order_item")
@Accessors(chain = true)
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder order;

    @OneToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Byte quantity;
}
