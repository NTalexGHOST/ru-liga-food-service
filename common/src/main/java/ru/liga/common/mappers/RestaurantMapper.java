package ru.liga.common.mappers;

import org.mapstruct.Mapper;

import ru.liga.common.dtos.DeliveryRestaurantDTO;
import ru.liga.common.dtos.FullMenuItemDTO;
import ru.liga.common.entities.MenuItem;
import ru.liga.common.entities.Restaurant;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    DeliveryRestaurantDTO restaurantToDeliveryRestaurantDTO(Restaurant restaurant);

    FullMenuItemDTO menuItemToFullMenuItemDTO(MenuItem menuItem);
    List<FullMenuItemDTO> menuItemsToFullMenuItemDTOs(List<MenuItem> menuItems);
}
