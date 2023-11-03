package ru.liga.common.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.liga.common.exceptions.*;
import ru.liga.common.responses.ErrorResponse;

@ControllerAdvice
public class RestaurantExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MenuItemNotFoundException.class)
    @ResponseBody
    public ErrorResponse handleMenuItemNotFoundException(MenuItemNotFoundException e) {

        return new ErrorResponse("404 Not Found", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RestaurantNotFoundException.class)
    @ResponseBody
    public ErrorResponse handleRestaurantNotFoundException(RestaurantNotFoundException e) {

        return new ErrorResponse("404 Not Found", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RestaurantClosedException.class)
    @ResponseBody
    public ErrorResponse handleRestaurantClosedException(RestaurantClosedException e) {

        return new ErrorResponse("505 Internal Server Error", e.getMessage());
    }
}
