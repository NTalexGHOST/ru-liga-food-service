package ru.liga.DeliveryService.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.liga.DeliveryService.services.DeliveryService;
import ru.liga.common.dtos.OrderStatusDTO;
import ru.liga.common.responses.CodeResponse;
import ru.liga.common.responses.DeliveryOrdersResponse;

@Tag(name = "Контроллер сервиса доставки")
@RestController
@RequestMapping("delivery-service")
@RequiredArgsConstructor
public class DeliveryRestController {

    private final DeliveryService deliveryService;

    @Operation(summary = "Смена статуса заявки")
    @PostMapping("/delivery/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CodeResponse changeDeliveryStatus(@PathVariable("id") Long id, @RequestBody OrderStatusDTO statusDTO) {

        return deliveryService.changeDeliveryStatus(id, statusDTO);
    }

    @Operation(summary = "Смена статуса заявки")
    @PostMapping("/deliveries")
    @ResponseStatus(HttpStatus.OK)
    public DeliveryOrdersResponse getAllDeliveries(@RequestBody OrderStatusDTO statusDTO) {

        return deliveryService.findAllDeliveries(statusDTO);
    }
}
