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
 * или иначе связаны с сервисом заказов
 */
@ControllerAdvice
public class OrderExceptionHandler {

    /**
     * Функция отвечает за обработку исключений типа {@link OrderNotFoundException}
     * @param e - отлавливаемое исключение
     * @return возвращает NOT_FOUND с описанием {@link CodeResponse}
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseBody
    public CodeResponse handleOrderNotFoundException(OrderNotFoundException e) {

        return new CodeResponse("404 Not Found", e.getMessage());
    }

    /**
     * Функция отвечает за обработку исключений типа {@link NoOrdersException}
     * @param e - отлавливаемое исключение
     * @return возвращает NOT_FOUND с описанием {@link CodeResponse}
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoOrdersException.class)
    @ResponseBody
    public CodeResponse handleNoOrdersException(NoOrdersException e) {

        return new CodeResponse("404 Not Found", e.getMessage());
    }

    /**
     * Функция отвечает за обработку исключений типа {@link OrderCreateFailedException}
     * @param e - отлавливаемое исключение
     * @return возвращает INTERNAL_SERVER_ERROR с описанием {@link CodeResponse}
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(OrderCreateFailedException.class)
    @ResponseBody
    public CodeResponse handleOrderCreateFailedException(OrderCreateFailedException e) {

        return new CodeResponse("505 Internal Server Error", e.getMessage());
    }

    /**
     * Функция отвечает за обработку исключений типа {@link OrderItemNotFoundException}
     * @param e - отлавливаемое исключение
     * @return возвращает NOT_FOUND с описанием {@link CodeResponse}
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderItemNotFoundException.class)
    @ResponseBody
    public CodeResponse handleOrderItemNotFoundException(OrderItemNotFoundException e) {

        return new CodeResponse("404 Not Found", e.getMessage());
    }

    /**
     * Функция отвечает за обработку исключений типа {@link NoOrderItemsException}
     * @param e - отлавливаемое исключение
     * @return возвращает NOT_FOUND с описанием {@link CodeResponse}
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoOrderItemsException.class)
    @ResponseBody
    public CodeResponse handleNoOrderItemsException(NoOrderItemsException e) {

        return new CodeResponse("404 Not Found", e.getMessage());
    }
}
