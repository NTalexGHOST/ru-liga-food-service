package ru.liga.common.exceptions;

public class OrderCreateFailedException extends RuntimeException {

    public OrderCreateFailedException(String message) {
        super(message);
    }
}
