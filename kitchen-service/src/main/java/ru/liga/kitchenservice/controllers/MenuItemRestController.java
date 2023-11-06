package ru.liga.kitchenservice.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import ru.liga.common.responses.RestaurantMenuResponse;
import ru.liga.kitchenservice.services.MenuItemService;

@Tag(name = "Контроллер сервиса доставки")
@RestController
@RequestMapping("kitchen-service")
@RequiredArgsConstructor
public class MenuItemRestController {

    private final MenuItemService menuItemService;

    @Operation(summary = "Полное меню ресторана")
    @GetMapping("/restaurant/{id}/menu")
    @ResponseStatus(HttpStatus.OK)
    public RestaurantMenuResponse getMenuById(@PathVariable("id") Long id) {

        return menuItemService.getMenuById(id);
    }
}
