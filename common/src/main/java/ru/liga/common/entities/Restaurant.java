package ru.liga.common.entities;

import lombok.*;

import ru.liga.common.statuses.RestaurantStatus;

import javax.persistence.*;

@Entity(name = "restaurant")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private RestaurantStatus status;
}
