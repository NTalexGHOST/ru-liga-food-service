package ru.liga.common.exceptions;

/**
 * Исключение OrderItemNotFoundException выбрасывается в случае, если не
 * удается найти конкретную позицию заказа, к примеру по ее id
 */
public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(String message) {
        super(message);
    }
}
