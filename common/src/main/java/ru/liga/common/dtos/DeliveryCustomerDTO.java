package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "DTO покупателя для сервиса доставки")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class DeliveryCustomerDTO {

    @Schema(description = "Координаты покупателя")
    @JsonProperty("address")
    private String address;

    @Schema(description = "Расстояние до ресторана")
    @JsonProperty("distance")
    private double distance;
}
