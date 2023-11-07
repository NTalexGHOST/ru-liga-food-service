package ru.liga.common.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "menu_item")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;
}
