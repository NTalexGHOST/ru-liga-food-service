package ru.liga.deliveryservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import ru.liga.common.dtos.OrderStatusDTO;
import ru.liga.common.entities.Courier;
import ru.liga.common.entities.CustomerOrder;
import ru.liga.common.exceptions.CourierNotFoundException;
import ru.liga.common.exceptions.OrderNotFoundException;
import ru.liga.common.repos.CourierRepository;
import ru.liga.common.repos.CustomerOrderRepository;
import ru.liga.common.statuses.CourierStatus;
import ru.liga.common.statuses.OrderStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QueueListener {

    private final ObjectMapper objectMapper;
    private final CourierRepository courierRepo;
    private final DeliveryService deliveryService;

    @RabbitListener(queues = "delivery-service")
    @SneakyThrows
    public void processQueue(String message) {

        CustomerOrder order = objectMapper.readValue(message, CustomerOrder.class);
        findCourierForOrder(order);
    }

    private void findCourierForOrder(CustomerOrder order){

        String restaurantCoords = order.getRestaurant().getAddress();
        List<Courier> freeCouriers = courierRepo.findAllByStatus(CourierStatus.FREE);
        
        HashMap<Long, Double> distances = null;
        freeCouriers.forEach(courier -> distances.put(courier.getId(),
                deliveryService.calculateDistance(restaurantCoords, courier.getCoordinates())));

        double closestDistance = 0; long closestCourierId = 0;
        for(Map.Entry<Long, Double> entry : distances.entrySet()) {
            closestDistance = Math.min(closestDistance, entry.getValue());
            closestCourierId = entry.getKey();
        }

        Courier courier;
        Optional<Courier> optionalCourier = courierRepo.findFirstById(closestCourierId);
        if (optionalCourier.isPresent()) courier = optionalCourier.get();
        else throw new CourierNotFoundException("Курьер с идентификатором " + closestCourierId + " не найден");

        courier.setStatus(CourierStatus.BUSY);
        courierRepo.save(courier);

        deliveryService.changeOrderStatus(order.getId(), new OrderStatusDTO(OrderStatus.DELIVERY_PICKING));
    }
}
