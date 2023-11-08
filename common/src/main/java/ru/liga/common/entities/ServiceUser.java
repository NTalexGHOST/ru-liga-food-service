package ru.liga.common.entities;

import lombok.*;
import ru.liga.common.statuses.CourierStatus;
import ru.liga.common.statuses.UserRole;

import javax.persistence.*;
import java.util.UUID;

@Entity(name = "service_user")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ServiceUser {

    @Id
    private UUID id = UUID.randomUUID();

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "account")
    private UUID account;
}
