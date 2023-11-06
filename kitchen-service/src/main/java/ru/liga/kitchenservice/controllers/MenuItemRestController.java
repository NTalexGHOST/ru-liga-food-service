package ru.liga.kitchenservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.liga.common.dtos.FullMenuItemDTO;
import ru.liga.common.responses.CodeResponse;
import ru.liga.common.responses.RestaurantMenuResponse;
import ru.liga.kitchenservice.dtos.MenuItemPriceDTO;
import ru.liga.kitchenservice.services.MenuItemService;

@Tag(name = "Контроллер сервиса доставки")
@RestController
@RequestMapping("kitchen-service")
@RequiredArgsConstructor
public class MenuItemRestController {

    private final MenuItemService menuItemService;

    @Operation(summary = "Полное меню ресторана")
    @GetMapping("/menu")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantMenuResponse getMenu() {

        return menuItemService.getMenu();
    }

    @Operation(summary = "Получение позиции меню")
    @GetMapping("/menu/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FullMenuItemDTO getMenuItemById(@PathVariable("id") Long id) {

        return menuItemService.getMenuItemById(id);
    }

    @Operation(summary = "Создание позиции меню")
    @PostMapping("/menu")
    @ResponseStatus(HttpStatus.OK)
    public CodeResponse createMenuItem(@RequestBody FullMenuItemDTO menuItemDTO) {

        return menuItemService.createMenuItem(menuItemDTO);
    }

    @Operation(summary = "Создание позиции меню")
    @DeleteMapping("/menu/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CodeResponse deleteMenuItem(@PathVariable("id") Long id) {

        return menuItemService.deleteMenuItem(id);
    }

    @Operation(summary = "Изменение цены позиции меню")
    @PutMapping("/menu/{id}/price")
    @ResponseStatus(HttpStatus.OK)
    public CodeResponse changeMenuItemPrice(@PathVariable("id") Long id, @RequestBody MenuItemPriceDTO priceDTO) {

        return menuItemService.changeMenuItemPrice(id, priceDTO);
    }
}
