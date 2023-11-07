package ru.liga.common.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.liga.common.exceptions.*;
import ru.liga.common.responses.CodeResponse;

@ControllerAdvice
public class DeliveryExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CourierNotFoundException.class)
    @ResponseBody
    public CodeResponse handleCourierNotFoundException(CourierNotFoundException e) {

        return new CodeResponse("404 Not Found", e.getMessage());
    }
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoCouriersException.class)
    @ResponseBody
    public CodeResponse handleNoCouriersException(NoCouriersException e) {

        return new CodeResponse("404 Not Found", e.getMessage());
    }
}
