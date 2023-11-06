package ru.liga.common.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.liga.common.exceptions.*;
import ru.liga.common.responses.CodeResponse;

@ControllerAdvice
public class RestaurantExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MenuItemNotFoundException.class)
    @ResponseBody
    public CodeResponse handleMenuItemNotFoundException(MenuItemNotFoundException e) {

        return new CodeResponse("404 Not Found", e.getMessage());
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoMenuItemsException.class)
    @ResponseBody
    public CodeResponse handleNoMenuItemsException(NoMenuItemsException e) {

        return new CodeResponse("404 Not Found", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RestaurantNotFoundException.class)
    @ResponseBody
    public CodeResponse handleRestaurantNotFoundException(RestaurantNotFoundException e) {

        return new CodeResponse("404 Not Found", e.getMessage());
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RestaurantClosedException.class)
    @ResponseBody
    public CodeResponse handleRestaurantClosedException(RestaurantClosedException e) {

        return new CodeResponse("505 Internal Server Error", e.getMessage());
    }
}
