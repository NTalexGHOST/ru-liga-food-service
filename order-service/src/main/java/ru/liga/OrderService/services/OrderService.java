package ru.liga.OrderService.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ru.liga.common.dtos.OrderDTO;
import ru.liga.common.dtos.RestaurantDTO;
import ru.liga.common.entities.CustomerOrder;
import ru.liga.common.entities.Restaurant;
import ru.liga.common.mappers.AllOrdersMapper;
import ru.liga.common.repos.CustomerOrderRepository;
import ru.liga.common.repos.RestaurantRepository;
import ru.liga.common.responses.AllOrdersResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = "ru.liga.common")
public class OrderService {

    private final CustomerOrderRepository orderRepo;
    private final RestaurantRepository restaurantRepo;
    private final AllOrdersMapper allOrdersMapper;

    public AllOrdersResponse getAllOrders() {

        List<CustomerOrder> orderEntities = orderRepo.findAll();
        if (orderEntities.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        List<OrderDTO> orderDTOs = allOrdersMapper.ordersToOrderDTOs(orderEntities);
        AllOrdersResponse response = new AllOrdersResponse(orderDTOs, 0, 10);

        return response;
    }

    public OrderDTO findOrderById(long id) {

        CustomerOrder order;
        Optional<CustomerOrder> optionalOrder = orderRepo.findFirstById(id);
        if (optionalOrder.isPresent()) order = optionalOrder.get();
        else throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return allOrdersMapper.orderToOrderDTO(order);
    }
}
