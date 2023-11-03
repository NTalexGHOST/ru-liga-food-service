package ru.liga.common.mappers;

import org.mapstruct.Mapper;

import ru.liga.common.dtos.RestaurantNameDTO;
import ru.liga.common.entities.Restaurant;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    RestaurantNameDTO restaurantToRestaurantNameDTO(Restaurant restaurant);
}
