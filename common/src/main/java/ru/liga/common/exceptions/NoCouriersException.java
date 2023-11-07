package ru.liga.common.exceptions;

/**
 * Исключение NoCouriersException выбрасывается в случае, если есть возможность
 * получить коллекцию курьеров, но при этом она возвращается пустой
 *
 * К примеру, если в БД нет вообще ни одного курьера или нет курьеров
 * с необходимым статусом
 */
public class NoCouriersException extends RuntimeException {
    public NoCouriersException(String message) {
        super(message);
    }
}
