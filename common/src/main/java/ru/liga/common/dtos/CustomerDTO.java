package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import java.awt.geom.Point2D;

@Schema(description = "DTO покупателя")
@Data
@Accessors(chain = true)
public class CustomerDTO {

    @Schema(description = "Идентификатор")
    @JsonProperty("id")
    private long id;

    @Schema(description = "Номер телефона")
    @JsonProperty("phone")
    private String phone;

    @Schema(description = "Электронная почта")
    @JsonProperty("email")
    @Email
    private String email;

    @Schema(description = "Координаты")
    @JsonProperty("address")
    private Point2D.Double address;
}
