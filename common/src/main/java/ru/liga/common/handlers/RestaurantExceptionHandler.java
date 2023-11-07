package ru.liga.common.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.liga.common.exceptions.*;
import ru.liga.common.responses.CodeResponse;

/**
 * Класс обработчик, отвечающий за любые исключения, которые так
 * или иначе связаны с сервисом ресторанов
 */
@ControllerAdvice
public class RestaurantExceptionHandler {

    /**
     * Функция отвечает за обработку исключений типа {@link MenuItemNotFoundException}
     * @param e - отлавливаемое исключение
     * Запрос, вызвавший исключение, возвращает код 404 NOT_FOUND
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MenuItemNotFoundException.class)
    public void handleMenuItemNotFoundException(MenuItemNotFoundException e) { }

    /**
     * Функция отвечает за обработку исключений типа {@link NoMenuItemsException}
     * @param e - отлавливаемое исключение
     * Запрос, вызвавший исключение, возвращает код 404 NOT_FOUND
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoMenuItemsException.class)
    public void handleNoMenuItemsException(NoMenuItemsException e) { }

    /**
     * Функция отвечает за обработку исключений типа {@link RestaurantNotFoundException}
     * @param e - отлавливаемое исключение
     * Запрос, вызвавший исключение, возвращает код 404 NOT_FOUND
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RestaurantNotFoundException.class)
    public void handleRestaurantNotFoundException(RestaurantNotFoundException e) { }

    /**
     * Функция отвечает за обработку исключений типа {@link RestaurantClosedException}
     * @param e - отлавливаемое исключение
     * Запрос, вызвавший исключение, возвращает код 500 INTERNAL_SERVER_ERROR
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RestaurantClosedException.class)
    public void handleRestaurantClosedException(RestaurantClosedException e) { }
}
