package ru.liga.common.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.liga.common.exceptions.NoOrdersException;
import ru.liga.common.exceptions.OrderNotFoundException;
import ru.liga.common.responses.AllOrdersResponse;
import ru.liga.common.responses.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleOrderNotFoundException(OrderNotFoundException e) {

        return new ErrorResponse("404 Not Found", e.getMessage());
    }

    @ExceptionHandler(NoOrdersException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public AllOrdersResponse handleNoOrdersException(NoOrdersException e) {

        return new AllOrdersResponse();
    }
}
