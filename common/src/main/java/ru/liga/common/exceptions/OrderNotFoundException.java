package ru.liga.common.exceptions;

/**
 * Исключение OrderNotFoundException выбрасывается в случае, если не
 * удается найти конкретный заказ, к примеру по его id
 */
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
