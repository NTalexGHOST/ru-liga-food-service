package ru.liga.kitchenservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import ru.liga.common.dtos.FullMenuItemDTO;
import ru.liga.common.dtos.FullOrderDTO;
import ru.liga.common.entities.MenuItem;
import ru.liga.common.entities.Restaurant;
import ru.liga.common.exceptions.NoMenuItemsException;
import ru.liga.common.mappers.RestaurantMapper;
import ru.liga.common.repos.MenuItemRepository;
import ru.liga.common.repos.RestaurantRepository;
import ru.liga.common.responses.RestaurantMenuResponse;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = "ru.liga.common")
public class MenuItemService {

    private final RestaurantRepository restaurantRepo;
    private final MenuItemRepository menuItemRepo;

    private final RestaurantMapper restaurantMapper;

    public RestaurantMenuResponse getMenuById(long id) {

        Restaurant restaurant;
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findFirstById(id);
        restaurant = optionalRestaurant.get();

        List<MenuItem> menuItems = menuItemRepo.findAllByRestaurant(restaurant);
        if (menuItems.isEmpty()) throw new NoMenuItemsException("В меню данного ресторана нет ни одной позиции");

        List<FullMenuItemDTO> menuItemDTOs = restaurantMapper.menuItemsToFullMenuItemDTOs(menuItems);

        return new RestaurantMenuResponse(menuItemDTOs, 0, 10);
    }
}
