package ru.liga.common.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import ru.liga.common.dtos.OrderDTO;
import ru.liga.common.dtos.RestaurantDTO;
import ru.liga.common.entities.CustomerOrder;
import ru.liga.common.entities.Restaurant;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AllOrdersMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    @Mapping(target = "status", ignore = true)
    RestaurantDTO restaurantToRestaurantDTO(Restaurant restaurant);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "courier", ignore = true)
    OrderDTO orderToOrderDTO(CustomerOrder order);
    List<OrderDTO> ordersToOrderDTOs(List<CustomerOrder> orders);

    //CustomerOrder

    /*@BeanMapping(ignoreByDefault = true)
    @Mapping(target = "price", ignore = false)
    @Mapping(target = "quantity", ignore = false)
    @Mapping(target = "description", source = "menuItem.description")
    @Mapping(target = "image", source = "menuItem.image")
    OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);
    List<OrderItemDTO> orderItemsToOrderItemDTOs(List<OrderItem> orderItems);*/
}
