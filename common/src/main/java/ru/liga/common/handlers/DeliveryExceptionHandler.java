package ru.liga.common.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import ru.liga.common.exceptions.CourierNotFoundException;
import ru.liga.common.exceptions.NoCouriersException;
import ru.liga.common.responses.CodeResponse;

/**
 * Класс обработчик, отвечающий за любые исключения, которые так
 * или иначе связаны с сервисом доставки
 */
@ControllerAdvice
public class DeliveryExceptionHandler {

    /**
     * Функция отвечает за обработку исключений типа {@link CourierNotFoundException}
     * @param e - отлавливаемое исключение
     * Запрос, вызвавший исключение, возвращает код 404 NOT_FOUND
     */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(CourierNotFoundException.class)
    public void handleCourierNotFoundException(CourierNotFoundException e) { }

    /**
     * Функция отвечает за обработку исключений типа {@link NoCouriersException}
     * @param e - отлавливаемое исключение
     * Запрос, вызвавший исключение, возвращает код 404 NOT_FOUND
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoCouriersException.class)
    public void handleNoCouriersException(NoCouriersException e) { }
}
