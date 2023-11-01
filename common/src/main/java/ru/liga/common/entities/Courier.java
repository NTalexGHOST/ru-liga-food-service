package ru.liga.common.entities;

import lombok.experimental.Accessors;

import ru.liga.common.statuses.CourierStatus;

import javax.persistence.*;

@Entity(name = "courier")
@Accessors(chain = true)
public class Courier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CourierStatus status;

    @Column(name = "coordinates")
    private String coordinates;
}
