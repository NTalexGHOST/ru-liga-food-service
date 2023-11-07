package ru.liga.common.entities;

import lombok.*;

import ru.liga.common.statuses.CourierStatus;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "courier")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Courier {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CourierStatus status;

    @Column(name = "coordinates")
    private String coordinates;
}
