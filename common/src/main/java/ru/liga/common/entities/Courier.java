package ru.liga.common.entities;

import lombok.*;

import ru.liga.common.statuses.CourierStatus;

import javax.persistence.*;

@Entity(name = "courier")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CourierStatus status;

    @Column(name = "coordinates")
    private String coordinates;
}
