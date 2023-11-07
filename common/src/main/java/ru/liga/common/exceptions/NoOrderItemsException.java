package ru.liga.common.exceptions;

/**
 * Исключение NoOrderItemsException выбрасывается в случае, если есть возможность
 * получить коллекцию позиций заказа, но при этом она возвращается пустой
 *
 * К примеру, если в БД нет вообще ни одного позиции какого-либо заказа или
 * их не существует у конкретного заказа
 */
public class NoOrderItemsException extends RuntimeException {
    public NoOrderItemsException(String message) {
        super(message);
    }
}
