package ru.liga.kitchenservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import ru.liga.common.dtos.FullMenuItemDTO;
import ru.liga.common.entities.MenuItem;
import ru.liga.common.entities.Restaurant;
import ru.liga.common.exceptions.MenuItemNotFoundException;
import ru.liga.common.exceptions.NoMenuItemsException;
import ru.liga.common.mappers.RestaurantMapper;
import ru.liga.common.repos.MenuItemRepository;
import ru.liga.common.repos.RestaurantRepository;
import ru.liga.common.responses.CodeResponse;
import ru.liga.common.responses.RestaurantMenuResponse;
import ru.liga.kitchenservice.dtos.MenuItemPriceDTO;

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

    public RestaurantMenuResponse getMenu() {

        //  Временная заглушка, пользователь (ресторан) позже будет подкручиваться из Spring Security
        Restaurant restaurant;
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findFirstById(1);
        restaurant = optionalRestaurant.get();

        List<MenuItem> menuItems = menuItemRepo.findAllByRestaurant(restaurant);
        if (menuItems.isEmpty()) throw new NoMenuItemsException("В меню данного ресторана нет ни одной позиции");

        List<FullMenuItemDTO> menuItemDTOs = restaurantMapper.menuItemsToFullMenuItemDTOs(menuItems);

        return new RestaurantMenuResponse(menuItemDTOs, 0, 10);
    }

    public FullMenuItemDTO getMenuItemById(long id) {

        MenuItem menuItem;
        Optional<MenuItem> optionalMenuItem = menuItemRepo.findFirstById(id);
        if (optionalMenuItem.isPresent()) menuItem = optionalMenuItem.get();
        else throw new MenuItemNotFoundException("Позиция в меню с идентификатором " + id + " не найдена");

        return restaurantMapper.menuItemToFullMenuItemDTO(menuItem);
    }

    public CodeResponse createMenuItem(FullMenuItemDTO menuItemDTO) {

        MenuItem menuItem = new MenuItem();

        //  Временная заглушка, пользователь (ресторан) позже будет подкручиваться из Spring Security
        Restaurant restaurant;
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findFirstById(1);
        restaurant = optionalRestaurant.get();
        menuItem.setRestaurant(restaurant);

        menuItem.setName(menuItemDTO.getName());
        menuItem.setPrice(menuItemDTO.getPrice());
        menuItem.setImage(menuItemDTO.getImage());
        menuItem.setDescription(menuItemDTO.getDescription());

        menuItemRepo.saveAndFlush(menuItem);

        return new CodeResponse("200 OK", "Позиция меню с id " + menuItem.getId() + " успешно создана");
    }

    public CodeResponse deleteMenuItem(long id) {

        MenuItem menuItem;
        Optional<MenuItem> optionalMenuItem = menuItemRepo.findFirstById(id);
        if (optionalMenuItem.isPresent()) menuItem = optionalMenuItem.get();
        else throw new MenuItemNotFoundException("Позиция в меню с идентификатором " + id + " не найдена");

        menuItemRepo.delete(menuItem);

        return new CodeResponse("200 OK", "Позиция меню с id " + menuItem.getId() + " успешно удалена");
    }

    public CodeResponse changeMenuItemPrice(long id, MenuItemPriceDTO priceDTO) {

        MenuItem menuItem;
        Optional<MenuItem> optionalMenuItem = menuItemRepo.findFirstById(id);
        if (optionalMenuItem.isPresent()) menuItem = optionalMenuItem.get();
        else throw new MenuItemNotFoundException("Позиция в меню с идентификатором " + id + " не найдена");

        menuItem.setPrice(priceDTO.getPrice());

        return new CodeResponse("200 OK", "Цена позиции меню с id " + menuItem.getId() +
                " успешно изменена на " + priceDTO.getPrice().toString());
    }
}
