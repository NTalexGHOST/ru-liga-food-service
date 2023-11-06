package ru.liga.kitchenservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import ru.liga.common.dtos.FullMenuItemDTO;
import ru.liga.common.dtos.FullOrderDTO;
import ru.liga.common.entities.MenuItem;
import ru.liga.common.entities.Restaurant;
import ru.liga.common.exceptions.MenuItemNotFoundException;
import ru.liga.common.exceptions.NoMenuItemsException;
import ru.liga.common.exceptions.OrderNotFoundException;
import ru.liga.common.exceptions.RestaurantNotFoundException;
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

    public RestaurantMenuResponse getMenuByRestaurantId(long id) {

        Restaurant restaurant;
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findFirstById(id);
        if (optionalRestaurant.isPresent()) restaurant = optionalRestaurant.get();
        else throw new RestaurantNotFoundException("Ресторан с идентификатором " + id + " не найден");

        List<MenuItem> menuItems = menuItemRepo.findAllByRestaurant(restaurant);
        if (menuItems.isEmpty()) throw new NoMenuItemsException("В меню данного ресторана нет ни одной позиции");

        List<FullMenuItemDTO> menuItemDTOs = restaurantMapper.menuItemsToFullMenuItemDTOs(menuItems);

        return new RestaurantMenuResponse(menuItemDTOs, 0, 10);
    }

    public FullMenuItemDTO getMenuItemById(long restaurantId, long itemId) {

        Restaurant restaurant;
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findFirstById(restaurantId);
        if (optionalRestaurant.isPresent()) restaurant = optionalRestaurant.get();
        else throw new RestaurantNotFoundException("Ресторан с идентификатором " + restaurantId + " не найден");

        MenuItem menuItem;
        Optional<MenuItem> optionalMenuItem = menuItemRepo.findFirstById(itemId);
        if (optionalMenuItem.isPresent()) menuItem = optionalMenuItem.get();
        else throw new MenuItemNotFoundException("Позиция в меню с идентификатором " + itemId + " не найдена");

        return restaurantMapper.menuItemToFullMenuItemDTO(menuItem);
    }
}
