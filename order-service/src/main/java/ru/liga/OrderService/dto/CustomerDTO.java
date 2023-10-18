package ru.liga.OrderService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

@Schema(description = "Сущность покупателя")
@Data
@Accessors(chain = true)
public class CustomerDTO {

    @Schema(description = "Идентификатор")
    private long id;

    @Schema(description = "Номер телефона")
    private String phone;

    @Schema(description = "Электронная почта")
    private String email;

    @Schema(description = "Физический адрес")
    private String address;
}
