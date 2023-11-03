package ru.liga.common.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "DTO позиции в ресторане")
@Data @Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class MenuItemDTO {

    @JsonIgnore
    private long id;

    @Schema(description = "Путь до фотографии")
    @JsonProperty("image")
    private String image;

    @Schema(description = "Описание позиции")
    @JsonProperty("description")
    private String description;
}
