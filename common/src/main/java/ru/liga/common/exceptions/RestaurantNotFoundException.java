package ru.liga.common.exceptions;

/**
 * Исключение RestaurantNotFoundException выбрасывается в случае, если не
 * удается найти конкретный ресторан, к примеру по его id
 */
public class RestaurantNotFoundException extends RuntimeException {
    public RestaurantNotFoundException(String message) {
        super(message);
    }
}
