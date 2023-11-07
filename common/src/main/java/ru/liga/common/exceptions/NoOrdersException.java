package ru.liga.common.exceptions;

/**
 * Исключение NoOrdersException выбрасывается в случае, если есть возможность
 * получить коллекцию заказов, но при этом она возвращается пустой
 *
 * К примеру, если в БД нет вообще ни одного заказа или же не существует заказов
 * с необходимым статусом или их еще нет у конкретного ресторана
 */
public class NoOrdersException extends RuntimeException {
    public NoOrdersException(String message) {
        super(message);
    }
}
