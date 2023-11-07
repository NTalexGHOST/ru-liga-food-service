package ru.liga.common.exceptions;

/**
 * Исключение MenuItemNotFoundException выбрасывается в случае, если не
 * удается найти конкретную позицию в меню ресторана, к примеру по ее id
 */
public class MenuItemNotFoundException extends RuntimeException {
    public MenuItemNotFoundException(String message) {
        super(message);
    }
}
