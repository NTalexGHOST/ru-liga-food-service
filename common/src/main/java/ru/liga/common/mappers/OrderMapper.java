package ru.liga.common.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.liga.common.dtos.FullOrderDTO;
import ru.liga.common.dtos.OrderItemDTO;
import ru.liga.common.dtos.RestaurantOrderDTO;
import ru.liga.common.entities.CustomerOrder;
import ru.liga.common.entities.OrderItem;

import java.util.List;

@Mapper(componentModel = "spring", uses = { RestaurantMapper.class })
public interface OrderMapper {

    @Mapping(target = "items", source = "orderItems")
    FullOrderDTO orderToOrderDTO(CustomerOrder order);
    List<FullOrderDTO> ordersToOrderDTOs(List<CustomerOrder> orders);

    @Mapping(target = "items", source = "orderItems")
    RestaurantOrderDTO orderToRestaurantOrderDTO(CustomerOrder order);
    List<RestaurantOrderDTO> ordersToRestaurantOrderDTOs(List<CustomerOrder> orders);

    @Mapping(target = "description", source = "menuItem.description")
    @Mapping(target = "image", source = "menuItem.image")
    OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);
    List<OrderItemDTO> orderItemsToOrderItemDTOs(List<OrderItem> orderItems);
}
