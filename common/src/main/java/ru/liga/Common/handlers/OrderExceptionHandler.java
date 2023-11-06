package ru.liga.common.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.liga.common.exceptions.NoOrdersException;
import ru.liga.common.exceptions.OrderCreateFailedException;
import ru.liga.common.exceptions.OrderNotFoundException;
import ru.liga.common.responses.CodeResponse;

@ControllerAdvice
public class OrderExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseBody
    public CodeResponse handleOrderNotFoundException(OrderNotFoundException e) {

        return new CodeResponse("404 Not Found", e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoOrdersException.class)
    @ResponseBody
    public CodeResponse handleNoOrdersException(NoOrdersException e) {

        return new CodeResponse("400 Not Found", e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(OrderCreateFailedException.class)
    @ResponseBody
    public CodeResponse handleOrderCreateFailedException(OrderCreateFailedException e) {

        return new CodeResponse("505 Internal Server Error", e.getMessage());
    }
}
