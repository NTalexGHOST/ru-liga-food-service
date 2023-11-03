package ru.liga.common.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.liga.common.exceptions.NoOrdersException;
import ru.liga.common.exceptions.OrderCreateFailedException;
import ru.liga.common.exceptions.OrderNotFoundException;
import ru.liga.common.responses.AllOrdersResponse;
import ru.liga.common.responses.ErrorResponse;

@ControllerAdvice
public class OrderExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseBody
    public ErrorResponse handleOrderNotFoundException(OrderNotFoundException e) {

        return new ErrorResponse("404 Not Found", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoOrdersException.class)
    @ResponseBody
    public AllOrdersResponse handleNoOrdersException(NoOrdersException e) {

        return new AllOrdersResponse();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(OrderCreateFailedException.class)
    @ResponseBody
    public ErrorResponse handleOrderCreateFailedException(OrderCreateFailedException e) {

        return new ErrorResponse("505 Internal Server Error", e.getMessage());
    }
}
