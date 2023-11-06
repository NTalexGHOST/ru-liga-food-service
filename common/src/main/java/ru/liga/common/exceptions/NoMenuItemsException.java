package ru.liga.common.exceptions;

public class NoMenuItemsException extends RuntimeException {
    public NoMenuItemsException(String message) {
        super(message);
    }
}
