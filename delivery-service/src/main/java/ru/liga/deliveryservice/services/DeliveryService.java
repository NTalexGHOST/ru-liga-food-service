package ru.liga.deliveryservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ru.liga.common.dtos.DeliveryOrderDTO;
import ru.liga.common.dtos.OrderItemDTO;
import ru.liga.common.dtos.OrderStatusDTO;
import ru.liga.common.entities.Courier;
import ru.liga.common.entities.CustomerOrder;
import ru.liga.common.exceptions.CourierNotFoundException;
import ru.liga.common.exceptions.NoCouriersException;
import ru.liga.common.exceptions.OrderNotFoundException;
import ru.liga.common.feign.OrderFeign;
import ru.liga.common.mappers.OrderMapper;
import ru.liga.common.repos.*;
import ru.liga.common.responses.DeliveryOrdersResponse;
import ru.liga.common.statuses.CourierStatus;
import ru.liga.common.statuses.OrderStatus;
import ru.liga.deliveryservice.producer.RabbitMQProducerServiceImpl;
import ru.liga.deliveryservice.utility.DistanceCalculator;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = "ru.liga.common")
public class DeliveryService {

    private final CustomerOrderRepository orderRepo;
    private final CourierRepository courierRepo;

    private final OrderFeign orderFeign;
    private final OrderMapper orderMapper;
    private final ObjectMapper objectMapper;
    private final RabbitMQProducerServiceImpl rabbit;

    @SneakyThrows
    public ResponseEntity<String> takeOrder(UUID id) {

        //  Получение текущего статуса заказа
        ResponseEntity<String> response = getOrderStatus(id);
        if (!response.getStatusCode().is2xxSuccessful()) return response;
        OrderStatus orderStatus = OrderStatus.valueOf(response.getBody());

        //  Возможно взять доставку заказа только со статусом DELIVERY_PENDING
        if(!orderStatus.equals(OrderStatus.DELIVERY_PENDING))
            return new ResponseEntity<>("Нельзя взять доставку заказа [" + id + "]", HttpStatus.INTERNAL_SERVER_ERROR);

        //  Установление заказу статуса DELIVERY_DELIVERING
        //  Еще существует статус DELIVERY_PICKING, но думаю он излишен и по своей сути дублирует DELIVERY_DELIVERING
        String message = objectMapper.writeValueAsString(new OrderStatusDTO(id, OrderStatus.DELIVERY_DELIVERING));
        rabbit.sendMessage(message, "customers");

        //  Здесь должно происходить изменение статуса авторизованного курьера на BUSY

        return response;
    }

    @SneakyThrows
    public ResponseEntity<String> completeOrder(UUID id) {

        //  Получение текущего статуса заказа
        ResponseEntity<String> response = getOrderStatus(id);
        if (!response.getStatusCode().is2xxSuccessful()) return response;
        OrderStatus orderStatus = OrderStatus.valueOf(response.getBody());

        //  Возможно завершить доставку заказа только со статусом DELIVERY_DELIVERING
        if(!orderStatus.equals(OrderStatus.DELIVERY_DELIVERING))
            return new ResponseEntity<>("Нельзя завершить доставку заказа [" + id + "]", HttpStatus.INTERNAL_SERVER_ERROR);

        //  Установление заказу статуса DELIVERY_COMPLETE
        String message = objectMapper.writeValueAsString(new OrderStatusDTO(id, OrderStatus.DELIVERY_COMPLETE));
        rabbit.sendMessage(message, "customers");

        //  Здесь должно происходить изменение статуса авторизованного курьера на FREE

        return response;
    }

    /**
     * Отвечает за получение списка всех заказов, которые могут взяты в доставку
     * @return возвращает список полученных заказов
     */
    public ResponseEntity<DeliveryOrdersResponse> findAllDeliveries() {

        List<CustomerOrder> orderEntities = orderRepo.findAllByStatus(OrderStatus.DELIVERY_PENDING);
        if (orderEntities.isEmpty())
            return new ResponseEntity<>(new DeliveryOrdersResponse(), HttpStatus.NOT_FOUND);

        List<DeliveryOrderDTO> orderDTOs = orderMapper.ordersToDeliveryOrderDTOs(orderEntities);

        orderDTOs.stream().forEach(orderDTO -> {
            //  Перемножение цены на кол-во каждой позиции для вычисления суммы заказа
            List<BigDecimal> totalPrices = null;
            orderDTO.getItems().stream().forEach(orderItemDTO ->
                    totalPrices.add(orderItemDTO.getPrice().multiply(BigDecimal.valueOf(orderItemDTO.getQuantity()))
            ));
            //  Умножение суммы на коэффициент оплаты курьеру, к примеру 0.1 = 10% от стоимости заказа
            BigDecimal payment = totalPrices.stream().reduce(BigDecimal::add).get().multiply(BigDecimal.valueOf(0.1));
            orderDTO.setPayment(payment);

            //  Получение координат курьера, ресторана и покупателя в строковом формате (x,y)
            String courierCoords = orderDTO.getCourier().getCoordinates();
            String restaurantCoords = orderDTO.getRestaurant().getAddress();
            String customerCoords = orderDTO.getCustomer().getAddress();

            //  Вычисление расстояния в км от курьера до ресторана и от ресторана до покупателя
            orderDTO.getRestaurant().setDistance(DistanceCalculator.calculateDistance(courierCoords, restaurantCoords));
            orderDTO.getCustomer().setDistance(DistanceCalculator.calculateDistance(restaurantCoords, customerCoords));
        });

        return ResponseEntity.ok(new DeliveryOrdersResponse(orderDTOs, 0, 10));
    }

    /**
     * Имитация оповещения всех курьеров о новом заказе
     * @param orderId - идентификатор заказа
     */
    public void findCourierForOrder(UUID orderId) {

        StringBuilder outputStringBuilder = new StringBuilder();
        outputStringBuilder.append("Поиск: ");

        CustomerOrder order;
        Optional<CustomerOrder> optionalOrder = orderRepo.findFirstById(orderId);
        if (optionalOrder.isPresent()) order = optionalOrder.get();
        else throw new OrderNotFoundException("Заказ с идентификатором " + orderId + " не найден");

        String restaurantCoords = order.getRestaurant().getAddress();
        List<Courier> freeCouriers = courierRepo.findAllByStatus(CourierStatus.FREE);
        if (freeCouriers.isEmpty()) outputStringBuilder.append("все курьеры в данный момент заняты");
        else outputStringBuilder.append("найдено " + freeCouriers.stream().count() + " свободных курьеров");

        HashMap<UUID, Double> distances = null;
        freeCouriers.stream().forEach(courier -> distances.put(courier.getId(),
                DistanceCalculator.calculateDistance(restaurantCoords, courier.getCoordinates())));

        //  Поиск самого ближайшего курьера
        double closestDistance = 0; UUID closestCourierId = null;
        for (Map.Entry<UUID, Double> entry : distances.entrySet()) {
            closestDistance = Math.min(closestDistance, entry.getValue());
            closestCourierId = entry.getKey();
            outputStringBuilder.append("\tКурьер [" + closestCourierId + "] - " + closestDistance + " км");
        }
        outputStringBuilder.append("Самый ближайший курьер [" + closestCourierId + "] - " + closestDistance + "км");

        System.out.println(outputStringBuilder);
    }

    private ResponseEntity<String> getOrderStatus(UUID id) {

        //  Получение текущего статуса заказа
        ResponseEntity<String> response = orderFeign.getOrderStatus(id);
        switch (response.getStatusCode()) {
            case OK:
                break;
            case NOT_FOUND:
                return new ResponseEntity<>("Заказ [" + id + "] не найден", HttpStatus.NOT_FOUND);
            default:
                return new ResponseEntity<>("Не удалось получить статус заказа [" + id + "]", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
