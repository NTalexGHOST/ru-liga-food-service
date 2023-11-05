package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "DTO ресторана для сервиса доставки")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DeliveryRestaurantDTO {

    @Schema(description = "Координаты ресторана")
    @JsonProperty("address")
    private String address;

    @Schema(description = "Расстояние до курьера")
    @JsonProperty("distance")
    private double distance;
}
