package ru.liga.KitchenService.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import ru.liga.common.dtos.FullOrderDTO;
import ru.liga.common.dtos.OrderStatusDTO;
import ru.liga.common.dtos.RestaurantOrderDTO;
import ru.liga.common.entities.CustomerOrder;
import ru.liga.common.entities.Restaurant;
import ru.liga.common.exceptions.NoOrdersException;
import ru.liga.common.exceptions.OrderNotFoundException;
import ru.liga.common.exceptions.RestaurantNotFoundException;
import ru.liga.common.mappers.OrderMapper;
import ru.liga.common.repos.*;
import ru.liga.common.responses.RestaurantOrdersResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = "ru.liga.common")
public class KitchenService {

    private final CustomerOrderRepository orderRepo;
    private final RestaurantRepository restaurantRepo;
    private final OrderMapper orderMapper;

    public RestaurantOrdersResponse findAllOrders(OrderStatusDTO statusDTO) {

        //  Временная заглушка для ресторана, так понимаю данная сущность тоже будет подкручиваться через Security
        Restaurant restaurant; long restaurantId = 1;
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findFirstById(restaurantId);
        if (optionalRestaurant.isPresent()) restaurant = optionalRestaurant.get();
        else throw new RestaurantNotFoundException("Ресторан с идентификатором " + restaurantId + " не найден");;

        List<CustomerOrder> orderEntities = orderRepo.findAllByRestaurantAndStatus(restaurant, statusDTO.getStatus());
        if (orderEntities.isEmpty()) throw new NoOrdersException("В базе данных нет записей ни об одном заказе");

        List<RestaurantOrderDTO> orderDTOs = orderMapper.ordersToRestaurantOrderDTOs(orderEntities);

        return new RestaurantOrdersResponse(orderDTOs, 0, 10);
    }
}
