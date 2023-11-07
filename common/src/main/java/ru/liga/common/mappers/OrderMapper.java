package ru.liga.common.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.liga.common.dtos.*;
import ru.liga.common.entities.Customer;
import ru.liga.common.entities.CustomerOrder;
import ru.liga.common.entities.OrderItem;

import java.util.List;

/**
 * Маппер, отвечающий за любые преобразования в сервисе заказов
 * Использует другие мапперы, {@link RestaurantMapper} и {@link CustomerMapper}
 */
@Mapper(componentModel = "spring", uses = { RestaurantMapper.class, CustomerMapper.class })
public interface OrderMapper {

    /**
     * Преобразовывает сущность {@link CustomerOrder} в соответствующую {@link FullOrderDTO},
     * при этом от вложенной сущности {@link ru.liga.common.entities.Restaurant} здесь берется только название
     * @param order - сущность заказа
     * @return возвращает {@link FullOrderDTO}
     */
    @Mapping(target = "items", source = "orderItems")
    @Mapping(target = "restaurantName", source = "restaurant.name")
    FullOrderDTO orderToOrderDTO(CustomerOrder order);
    /**
     * Преобразовывает список сущностей {@link CustomerOrder} в соответствующий список {@link FullOrderDTO},
     * @param orders - список сущностей заказа
     * @return возвращает список {@link FullOrderDTO}
     */
    List<FullOrderDTO> ordersToOrderDTOs(List<CustomerOrder> orders);

    /**
     * Преобразовывает сущность {@link CustomerOrder} в соответствующую {@link RestaurantOrderDTO},
     * @param order - сущность заказа
     * @return возвращает {@link RestaurantOrderDTO}
     */
    @Mapping(target = "items", source = "orderItems")
    RestaurantOrderDTO orderToRestaurantOrderDTO(CustomerOrder order);
    /**
     * Преобразовывает список сущностей {@link CustomerOrder} в соответствующий список {@link RestaurantOrderDTO},
     * @param orders - список сущностей заказа
     * @return возвращает список {@link RestaurantOrderDTO}
     */
    List<RestaurantOrderDTO> ordersToRestaurantOrderDTOs(List<CustomerOrder> orders);

    /**
     * Преобразовывает сущность {@link OrderItem} в соответствующую {@link OrderItemDTO}, при этом от
     * вложенной сущности {@link ru.liga.common.entities.MenuItem} здесь берется описание и фото позиции
     * @param orderItem - сущность позиции заказа
     * @return возвращает {@link OrderItemDTO}
     */
    @Mapping(target = "description", source = "menuItem.description")
    @Mapping(target = "image", source = "menuItem.image")
    OrderItemDTO orderItemToOrderItemDTO(OrderItem orderItem);
    /**
     * Преобразовывает список сущностей {@link OrderItem} в соответствующий список {@link OrderItemDTO},
     * @param orderItems - список сущностей позиции заказа
     * @return возвращает список {@link OrderItemDTO}
     */
    List<OrderItemDTO> orderItemsToOrderItemDTOs(List<OrderItem> orderItems);

    /**
     * Преобразовывает сущность {@link OrderItem} в соответствующую {@link FullOrderItemDTO}, при этом от
     * вложенной сущности {@link ru.liga.common.entities.MenuItem} здесь берется название, описание и фото позиции
     * @param orderItem - сущность позиции заказа
     * @return возвращает {@link FullOrderItemDTO}
     */
    @Mapping(target = "name", source = "menuItem.name")
    @Mapping(target = "description", source = "menuItem.description")
    @Mapping(target = "image", source = "menuItem.image")
    FullOrderItemDTO orderItemToFullOrderItemDTO(OrderItem orderItem);
    /**
     * Преобразовывает список сущностей {@link OrderItem} в соответствующий список {@link FullOrderItemDTO},
     * @param orderItems - список сущностей позиции заказа
     * @return возвращает список {@link FullOrderItemDTO}
     */
    List<FullOrderItemDTO> orderItemsToFullOrderItemDTOs(List<OrderItem> orderItems);

    /**
     * Преобразовывает сущность {@link CustomerOrder} в соответствующую {@link DeliveryOrderDTO}
     * @param order - сущность заказа
     * @return возвращает {@link DeliveryOrderDTO}
     */
    DeliveryOrderDTO orderToDeliveryOrderDTO(CustomerOrder order);
    /**
     * Преобразовывает список сущностей {@link CustomerOrder} в соответствующий список {@link DeliveryOrderDTO},
     * @param orders - список сущностей заказа
     * @return возвращает список {@link DeliveryOrderDTO}
     */
    List<DeliveryOrderDTO> ordersToDeliveryOrderDTOs(List<CustomerOrder> orders);
}
