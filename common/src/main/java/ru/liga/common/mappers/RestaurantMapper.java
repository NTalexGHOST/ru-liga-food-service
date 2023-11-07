package ru.liga.common.mappers;

import org.mapstruct.Mapper;

import ru.liga.common.dtos.DeliveryOrderDTO;
import ru.liga.common.dtos.DeliveryRestaurantDTO;
import ru.liga.common.dtos.FullMenuItemDTO;
import ru.liga.common.dtos.FullOrderItemDTO;
import ru.liga.common.entities.CustomerOrder;
import ru.liga.common.entities.MenuItem;
import ru.liga.common.entities.Restaurant;

import java.util.List;

/**
 * Маппер, отвечающий за любые преобразования в сервисе ресторанов
 */
@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    /**
     * Преобразовывает сущность {@link Restaurant} в соответствующую {@link DeliveryRestaurantDTO}
     * @param restaurant - сущность ресторана
     * @return возвращает {@link DeliveryRestaurantDTO}
     */
    DeliveryRestaurantDTO restaurantToDeliveryRestaurantDTO(Restaurant restaurant);

    /**
     * Преобразовывает сущность {@link MenuItem} в соответствующую {@link FullMenuItemDTO}
     * @param menuItem - сущность позиции меню ресторана
     * @return возвращает {@link FullMenuItemDTO}
     */
    FullMenuItemDTO menuItemToFullMenuItemDTO(MenuItem menuItem);
    /**
     * Преобразовывает список сущностей {@link MenuItem} в соответствующий список {@link FullMenuItemDTO}
     * @param menuItems - список сущностей позиции меню ресторана
     * @return возвращает список {@link FullMenuItemDTO}
     */
    List<FullMenuItemDTO> menuItemsToFullMenuItemDTOs(List<MenuItem> menuItems);
}
