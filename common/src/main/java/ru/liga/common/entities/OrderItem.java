package ru.liga.common.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity(name = "order_item")
@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private CustomerOrder order;

    @OneToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "quantity")
    private Byte quantity;
}
