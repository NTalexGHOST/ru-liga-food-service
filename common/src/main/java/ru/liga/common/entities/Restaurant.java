package ru.liga.common.entities;

import lombok.experimental.Accessors;

import ru.liga.common.statuses.RestaurantStatus;

import javax.persistence.*;

@Entity(name = "restaurant")
@Accessors(chain = true)
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RestaurantStatus status;
}
