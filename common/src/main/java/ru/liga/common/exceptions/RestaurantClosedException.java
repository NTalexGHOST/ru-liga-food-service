package ru.liga.common.exceptions;

/**
 * Исключение RestaurantClosedException выбрасывается в случае, если конкретный
 * ресторан находится в статусе CLOSED, т.е. закрыт в данный момент
 */
public class RestaurantClosedException extends RuntimeException {
    public RestaurantClosedException(String message) {
        super(message);
    }
}
