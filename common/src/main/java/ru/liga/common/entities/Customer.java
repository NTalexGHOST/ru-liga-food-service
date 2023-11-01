package ru.liga.common.entities;

import lombok.experimental.Accessors;

import javax.persistence.*;

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
    private String address;
}
