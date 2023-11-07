package ru.liga.common.exceptions;

/**
 * Исключение CourierNotFoundException выбрасывается в случае,
 * если не удается найти конкретного курьера, к примеру по его id
 */
public class CourierNotFoundException extends RuntimeException {
    public CourierNotFoundException(String message) {
        super(message);
    }
}
