package ru.liga.OrderService.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CustomerDTO {
    private long id;

    private byte phone;

    private String email;

    private String address;
}
