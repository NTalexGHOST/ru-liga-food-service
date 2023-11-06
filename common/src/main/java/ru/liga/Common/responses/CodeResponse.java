package ru.liga.common.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "DTO для вывода исключений")
@Data @Getter @Setter
@AllArgsConstructor
public class CodeResponse {

    @Schema(description = "Код ошибки")
    @JsonProperty("code")
    private String code;

    @Schema(description = "Описание исключения")
    @JsonProperty("message")
    private String message;
}
