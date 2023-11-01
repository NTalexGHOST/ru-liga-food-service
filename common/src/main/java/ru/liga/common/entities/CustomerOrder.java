package ru.liga.common.entities;

import lombok.*;

import ru.liga.common.statuses.OrderStatus;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity(name = "customer_order")
@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private Courier courier = null;

    @Column(name = "timestamp")
    private Timestamp timestamp;
}
