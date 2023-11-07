package ru.liga.common.exceptions;

/**
 * Исключение NoMenuItemsException выбрасывается в случае, если есть возможность
 * получить коллекцию позиций меню ресторана, но при этом она возвращается пустой
 *
 * К примеру, если в БД нет вообще ни одной позиций меню в принципе или у
 * конкретного ресторана.
 */
public class NoMenuItemsException extends RuntimeException {
    public NoMenuItemsException(String message) {
        super(message);
    }
}
