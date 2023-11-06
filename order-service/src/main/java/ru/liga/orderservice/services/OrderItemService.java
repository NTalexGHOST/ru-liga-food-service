package ru.liga.orderservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import ru.liga.common.dtos.FullMenuItemDTO;
import ru.liga.common.dtos.FullOrderItemDTO;
import ru.liga.common.entities.CustomerOrder;
import ru.liga.common.entities.MenuItem;
import ru.liga.common.entities.OrderItem;
import ru.liga.common.exceptions.NoMenuItemsException;
import ru.liga.common.exceptions.NoOrderItemsException;
import ru.liga.common.exceptions.OrderItemNotFoundException;
import ru.liga.common.exceptions.OrderNotFoundException;
import ru.liga.common.mappers.OrderMapper;
import ru.liga.common.repos.*;
import ru.liga.common.responses.OrderItemsResponse;
import ru.liga.common.responses.RestaurantMenuResponse;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = "ru.liga.common")
public class OrderItemService {

    private final CustomerOrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final MenuItemRepository menuItemRepo;
    private final RestaurantRepository restaurantRepo;
    private final CustomerRepository customerRepo;

    //  Временно, в 6 задании появится взаимодействие с сервисом доставки для поиска курьера
    private final CourierRepository courierRepo;

    private final OrderMapper orderMapper;

    public OrderItemsResponse getOrderItemsByOrderId(long id) {

        CustomerOrder order;
        Optional<CustomerOrder> optionalOrder = orderRepo.findFirstById(id);
        if (optionalOrder.isPresent()) order = optionalOrder.get();
        else throw new OrderNotFoundException("Заказ с идентификатором " + id + " не найдена");

        List<OrderItem> orderItems = orderItemRepo.findAllByOrder(order);
        if (orderItems.isEmpty()) throw new NoOrderItemsException("В данном заказе нет ни одной позиции");

        List<FullOrderItemDTO> orderItemDTOs = orderMapper.orderItemsToFullOrderItemDTOs(orderItems);

        return new OrderItemsResponse(orderItemDTOs, 0, 10);
    }

    public FullOrderItemDTO getOrderItemById(long id) {

        OrderItem orderItem;
        Optional<OrderItem> optionalOrderItem = orderItemRepo.findFirstById(id);
        if (optionalOrderItem.isPresent()) orderItem = optionalOrderItem.get();
        else throw new OrderItemNotFoundException("Позиция заказа с идентификатором " + id + " не найдена");

        return orderMapper.orderItemToFullOrderItemDTO(orderItem);
    }
}
