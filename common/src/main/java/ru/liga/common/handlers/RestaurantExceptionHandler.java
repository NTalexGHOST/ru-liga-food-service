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
     * @return возвращает NOT_FOUND с описанием {@link CodeResponse}
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MenuItemNotFoundException.class)
    @ResponseBody
    public CodeResponse handleMenuItemNotFoundException(MenuItemNotFoundException e) {

        return new CodeResponse("404 Not Found", e.getMessage());
    }

    /**
     * Функция отвечает за обработку исключений типа {@link NoMenuItemsException}
     * @param e - отлавливаемое исключение
     * @return возвращает NOT_FOUND с описанием {@link CodeResponse}
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoMenuItemsException.class)
    @ResponseBody
    public CodeResponse handleNoMenuItemsException(NoMenuItemsException e) {

        return new CodeResponse("404 Not Found", e.getMessage());
    }

    /**
     * Функция отвечает за обработку исключений типа {@link RestaurantNotFoundException}
     * @param e - отлавливаемое исключение
     * @return возвращает NOT_FOUND с описанием {@link CodeResponse}
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RestaurantNotFoundException.class)
    @ResponseBody
    public CodeResponse handleRestaurantNotFoundException(RestaurantNotFoundException e) {

        return new CodeResponse("404 Not Found", e.getMessage());
    }

    /**
     * Функция отвечает за обработку исключений типа {@link RestaurantClosedException}
     * @param e - отлавливаемое исключение
     * @return возвращает INTERNAL_SERVER_ERROR с описанием {@link CodeResponse}
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RestaurantClosedException.class)
    @ResponseBody
    public CodeResponse handleRestaurantClosedException(RestaurantClosedException e) {

        return new CodeResponse("505 Internal Server Error", e.getMessage());
    }
}
