package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;

@Schema(description = "DTO покупателя")
@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private String address;
}
