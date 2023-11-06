package ru.liga.common.statuses;

public enum CourierStatus {

    FREE, // Курьер свободен и ждет назначения ему заказа
    BUSY, // Курьер доставляет заказ
    REST // У курьера перерыв или выходной
}
