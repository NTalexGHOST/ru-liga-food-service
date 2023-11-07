package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import ru.liga.common.statuses.CourierStatus;

@Schema(description = "DTO курьера")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CourierDTO {

    @Schema(description = "Идентификатор")
    @JsonProperty("id")
    private long id;

    @Schema(description = "Номер телефона")
    @JsonProperty("phone")
    private String phone;

    @Schema(description = "Статус курьера", allowableValues = { "FREE", "BUSY", "REST" })
    private CourierStatus status;

    @Schema(description = "Координаты")
    private String coordinates;
}
