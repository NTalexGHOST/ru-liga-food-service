package ru.liga.orderservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import ru.liga.common.dtos.FullMenuItemDTO;
import ru.liga.common.dtos.FullOrderItemDTO;
import ru.liga.common.dtos.ShortOrderItemDTO;
import ru.liga.common.entities.Customer;
import ru.liga.common.entities.CustomerOrder;
import ru.liga.common.entities.MenuItem;
import ru.liga.common.entities.OrderItem;
import ru.liga.common.exceptions.*;
import ru.liga.common.mappers.OrderMapper;
import ru.liga.common.repos.*;
import ru.liga.common.responses.CodeResponse;
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

    public CodeResponse createOrderItem(long orderId, ShortOrderItemDTO orderItemDTO) {

        OrderItem orderItem = new OrderItem();

        CustomerOrder order;
        Optional<CustomerOrder> optionalOrder = orderRepo.findFirstById(orderId);
        if (optionalOrder.isPresent()) order = optionalOrder.get();
        else throw new OrderNotFoundException("Заказ с идентификатором " + orderId + " не найден");
        orderItem.setOrder(order);

        MenuItem menuItem; long menuItemId = orderItemDTO.getMenuItemId();
        Optional<MenuItem> optionalMenuItem = menuItemRepo.findFirstById(menuItemId);
        if (optionalMenuItem.isPresent()) menuItem = optionalMenuItem.get();
        else throw new MenuItemNotFoundException("Позиция в меню с идентификатором " + menuItemId + " не найдена");
        orderItem.setMenuItem(menuItem);

        orderItem.setPrice(menuItem.getPrice());
        orderItem.setQuantity(orderItemDTO.getQuantity());

        orderItemRepo.saveAndFlush(orderItem);

        return new CodeResponse("200 OK", "Позиция заказа с id " + orderItem.getId() + " успешно создана");
    }

    public CodeResponse deleteOrderItem(long id) {

        OrderItem orderItem;
        Optional<OrderItem> optionalOrderItem = orderItemRepo.findFirstById(id);
        if (optionalOrderItem.isPresent()) orderItem = optionalOrderItem.get();
        else throw new OrderItemNotFoundException("Позиция заказа с идентификатором " + id + " не найдена");

        orderItemRepo.delete(orderItem);

        return new CodeResponse("200 OK", "Позиция заказа с id " + orderItem.getId() + " успешно удалена");
    }
}
