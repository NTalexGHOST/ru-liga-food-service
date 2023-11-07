package ru.liga.common.exceptions;

/**
 * Исключение OrderCreateFailedException выбрасывается в случае, если произошла
 * любая внутрення ошибка сервера при попытке создания заказа
 */
public class OrderCreateFailedException extends RuntimeException {

    public OrderCreateFailedException(String message) {
        super(message);
    }
}
