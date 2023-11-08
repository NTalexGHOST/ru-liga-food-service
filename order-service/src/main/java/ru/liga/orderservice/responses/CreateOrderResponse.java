package ru.liga.orderservice.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Schema(description = "DTO для ответа после успешного создания заказа")
@Data @Getter @Setter
@AllArgsConstructor
public class CreateOrderResponse {

    @Schema(description = "Идентификатор созданного заказа")
    @JsonProperty("id")
    private UUID id;

    @Schema(description = "URL для оплаты заказа")
    @JsonProperty("secret_payment_url")
    private String secretPaymentUrl;

    @Schema(description = "Примерное время ожидания заказа")
    @JsonProperty("estimated_time_of_arrival")
    private String estimatedTimeOfArrival;
}
