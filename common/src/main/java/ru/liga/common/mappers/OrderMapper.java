package ru.liga.common.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.liga.common.dtos.*;
import ru.liga.common.entities.CustomerOrder;
import ru.liga.common.entities.OrderItem;

import java.util.List;

@Mapper(componentModel = "spring", uses = { RestaurantMapper.class, CustomerMapper.class })
public interface OrderMapper {

    @Mapping(target = "items", source = "orderItems")
    @Mapping(target = "restaurantName", source = "restaurant.name")
    FullOrderDTO orderToOrderDTO(CustomerOrder order);
    List<FullOrderDTO> ordersToOrderDTOs(List<CustomerOrder> orders);

    @Mapping(target = "items", source = "orderItems")
    RestaurantOrderDTO orderToRestaurantOrderDTO(CustomerOrder order);
    List<RestaurantOrderDTO> ordersToRestaurantOrderDTOs(List<CustomerOrder> orders);

    @Mapping(target = "description", source = "menuItem.description")
    @Mapping(target = "image", source = "menuItem.image")
    OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);
    List<OrderItemDTO> orderItemsToOrderItemDTOs(List<OrderItem> orderItems);

    @Mapping(target = "name", source = "menuItem.name")
    @Mapping(target = "description", source = "menuItem.description")
    @Mapping(target = "image", source = "menuItem.image")
    FullOrderItemDTO orderItemToFullOrderItemDTO(OrderItem orderItem);
    List<FullOrderItemDTO> orderItemsToFullOrderItemDTOs(List<OrderItem> orderItems);

    DeliveryOrderDTO orderToDeliveryOrderDTO(CustomerOrder order);
    List<DeliveryOrderDTO> ordersToDeliveryOrderDTOs(List<CustomerOrder> orders);
}
