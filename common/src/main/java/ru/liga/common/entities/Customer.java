package ru.liga.common.entities;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "customer")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Customer {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;
}
