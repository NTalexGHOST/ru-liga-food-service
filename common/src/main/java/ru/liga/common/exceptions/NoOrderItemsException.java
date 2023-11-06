package ru.liga.common.exceptions;

public class NoOrderItemsException extends RuntimeException {
    public NoOrderItemsException(String message) {
        super(message);
    }
}
