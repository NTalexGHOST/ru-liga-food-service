package ru.liga.common.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.liga.common.dtos.OrderDTO;
import ru.liga.common.dtos.OrderItemDTO;
import ru.liga.common.entities.CustomerOrder;
import ru.liga.common.entities.OrderItem;

import java.util.List;

@Mapper(componentModel = "spring", uses = { RestaurantMapper.class })
public interface OrderMapper {

    @Mapping(target = "items", source = "orderItems")
    OrderDTO orderToOrderDTO(CustomerOrder order);
    List<OrderDTO> ordersToOrderDTOs(List<CustomerOrder> orders);

    @Mapping(target = "description", source = "menuItem.description")
    @Mapping(target = "image", source = "menuItem.image")
    OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);
    List<OrderItemDTO> orderItemsToOrderItemDTOs(List<OrderItem> orderItems);
}
