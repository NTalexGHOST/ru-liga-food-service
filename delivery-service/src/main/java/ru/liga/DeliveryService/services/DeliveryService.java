package ru.liga.DeliveryService.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import ru.liga.common.dtos.OrderStatusDTO;
import ru.liga.common.entities.CustomerOrder;
import ru.liga.common.exceptions.OrderNotFoundException;
import ru.liga.common.repos.*;
import ru.liga.common.responses.CodeResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = "ru.liga.common")
public class DeliveryService {

    private final CustomerOrderRepository orderRepo;
    private final CourierRepository courierRepo;

    public CodeResponse changeDeliveryStatus(long id, OrderStatusDTO statusDTO) {

        //  Временная заглушка для смены статуса заказа, позже здесь будет запрос к order-service
        CustomerOrder order;
        Optional<CustomerOrder> optionalOrder = orderRepo.findFirstById(id);
        if (optionalOrder.isPresent()) order = optionalOrder.get();
        else throw new OrderNotFoundException("Заказ с идентификатором " + id + " не найден");

        order.setStatus(statusDTO.getStatus());

        return new CodeResponse("200 OK", "Статус доставки успешно изменен на " + "");
    }
}
