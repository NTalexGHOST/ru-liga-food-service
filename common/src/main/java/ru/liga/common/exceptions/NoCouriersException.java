package ru.liga.common.exceptions;

public class NoCouriersException extends RuntimeException {
    public NoCouriersException(String message) {
        super(message);
    }
}
