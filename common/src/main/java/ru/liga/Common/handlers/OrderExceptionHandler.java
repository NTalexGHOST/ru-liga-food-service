package ru.liga.common.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.liga.common.exceptions.*;

/**
 * Класс обработчик, отвечающий за любые исключения, которые так
 * или иначе связаны с сервисом заказов
 */
@ControllerAdvice
public class OrderExceptionHandler {

    /**
     * Функция отвечает за обработку исключений типа {@link JsonProcessingException}
     * @param e - отлавливаемое исключение
     * Запрос, вызвавший исключение, возвращает код 500 INTERNAL_SERVER_ERROR
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(JsonProcessingException.class)
    public void handleJsonProcessingException(JsonProcessingException e) { }

    /**
     * Функция отвечает за обработку исключений типа {@link OrderNotFoundException}
     * @param e - отлавливаемое исключение
     * Запрос, вызвавший исключение, возвращает код 404 NOT_FOUND
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderNotFoundException.class)
    public void handleOrderNotFoundException(OrderNotFoundException e) { }

    /**
     * Функция отвечает за обработку исключений типа {@link NoOrdersException}
     * @param e - отлавливаемое исключение
     * Запрос, вызвавший исключение, возвращает код 404 NOT_FOUND
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoOrdersException.class)
    public void handleNoOrdersException(NoOrdersException e) { }

    /**
     * Функция отвечает за обработку исключений типа {@link OrderCreateFailedException}
     * @param e - отлавливаемое исключение
     * Запрос, вызвавший исключение, возвращает код 500 INTERNAL_SERVER_ERROR
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(OrderCreateFailedException.class)
    public void handleOrderCreateFailedException(OrderCreateFailedException e) { }

    /**
     * Функция отвечает за обработку исключений типа {@link OrderItemNotFoundException}
     * @param e - отлавливаемое исключение
     * Запрос, вызвавший исключение, возвращает код 404 NOT_FOUND
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(OrderItemNotFoundException.class)
    public void handleOrderItemNotFoundException(OrderItemNotFoundException e) { }

    /**
     * Функция отвечает за обработку исключений типа {@link NoOrderItemsException}
     * @param e - отлавливаемое исключение
     * Запрос, вызвавший исключение, возвращает код 404 NOT_FOUND
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoOrderItemsException.class)
    public void handleNoOrderItemsException(NoOrderItemsException e) { }
}
