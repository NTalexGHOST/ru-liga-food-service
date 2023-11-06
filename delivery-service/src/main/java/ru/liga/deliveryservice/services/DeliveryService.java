package ru.liga.deliveryservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import ru.liga.common.dtos.DeliveryOrderDTO;
import ru.liga.common.dtos.OrderItemDTO;
import ru.liga.common.entities.CustomerOrder;
import ru.liga.common.exceptions.NoOrdersException;
import ru.liga.common.feign.OrderFeign;
import ru.liga.common.mappers.OrderMapper;
import ru.liga.common.repos.*;
import ru.liga.common.responses.CodeResponse;
import ru.liga.common.responses.DeliveryOrdersResponse;
import ru.liga.common.statuses.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = "ru.liga.common")
public class DeliveryService {

    private final CustomerOrderRepository orderRepo;
    private final OrderFeign orderFeign;
    private final OrderMapper orderMapper;

    public CodeResponse changeOrderStatus(long id, OrderStatus status) {

        CodeResponse codeResponse = orderFeign.changeOrderStatus(id, status);

        return codeResponse;
    }

    public DeliveryOrdersResponse findAllDeliveries(OrderStatus status) {

        List<CustomerOrder> orderEntities = orderRepo.findAllByStatus(status);
        if (orderEntities.isEmpty())
            throw new NoOrdersException("В базе данных нет заказов со статусом " + status);

        List<DeliveryOrderDTO> orderDTOs = orderMapper.ordersToDeliveryOrderDTOs(orderEntities);

        orderDTOs.forEach(orderDTO -> {
            BigDecimal payment = orderDTO.getItems().stream().map(OrderItemDTO::getPrice).reduce(BigDecimal::add).get();
            orderDTO.setPayment(payment);

            String courierCoords = orderDTO.getCourier().getCoordinates();
            String restaurantCoords = orderDTO.getRestaurant().getAddress();
            orderDTO.getRestaurant().setDistance(calculateDistance(courierCoords, restaurantCoords));

            String customerCoords = orderDTO.getCustomer().getAddress();
            orderDTO.getCustomer().setDistance(calculateDistance(restaurantCoords, customerCoords));
        });

        return new DeliveryOrdersResponse(orderDTOs, 0, 10);
    }

    //  Использование формулы Ламберта для длинных линий
    public double calculateDistance(String firstCoords, String secondCoords) {

        int earthRadius = 6371;

        Point firstPoint = coordsToPoint(firstCoords);
        Point secondPoint = coordsToPoint(secondCoords);

        double latitudeDifference = degreesToRadians(secondPoint.getX() - firstPoint.getX());
        double longitudeDifference = degreesToRadians(secondPoint.getY() - firstPoint.getY());

        double firstLatitude = degreesToRadians(firstPoint.getX());
        double secondLatitude = degreesToRadians(secondPoint.getX());

        double a = Math.pow(Math.sin(latitudeDifference / 2), 2) + Math.pow(Math.sin(longitudeDifference / 2), 2) *
                Math.cos(firstLatitude) * Math.cos(secondLatitude);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }

    private Point coordsToPoint(String coordsString) {

        coordsString = coordsString.replaceAll("() ", "");
        String[] coords = coordsString.split(",");

        return new Point(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
    }

    private double degreesToRadians(double degrees) {

        return degrees * Math.PI / 180;
    }
}
