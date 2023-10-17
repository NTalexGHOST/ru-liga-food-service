package ru.liga.OrderService.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.liga.OrderService.dto.Statuses.RestaurantStatus;

@Schema(description = "Сущность ресторана")
@Data
@Accessors(chain = true)
public class RestaurantDTO {

    @Schema(description = "Идентификатор")
    @JsonIgnore
    private long id;

    @Schema(description = "Название ресторана")
    private String name;

    @Schema(description = "Физический адрес")
    @JsonIgnore
    private String address;

    @Schema(description = "Статус ресторана", allowableValues = { "CLOSED", "OPEN", "OVERFLOWED" })
    @JsonIgnore
    private RestaurantStatus status;
}
