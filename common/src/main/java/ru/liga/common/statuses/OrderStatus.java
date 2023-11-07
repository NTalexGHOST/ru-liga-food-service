package ru.liga.common.statuses;

/** Перечисление возможных статусов заказа */
public enum OrderStatus {

    /** В корзину были добавлены товары и покупатель подтвердил заказ */
    CUSTOMER_CREATED,
    /** Заказ был успешно оплачен */
    CUSTOMER_PAID,
    /** Заказ был успешно отменен покупателем */
    CUSTOMER_CANCELLED,

    /** Заказ принят рестораном */
    KITCHEN_ACCEPTED,
    /** Заказ готовится */
    KITCHEN_PREPARING,
    /** Заказ отклонен рестораном (к примеру, закончились ингредиенты для какого-либо блюда) */
    KITCHEN_DENIED,
    /** Возврат заказа рестораном */
    KITCHEN_REFUNDED,

    /** Заказ приготовлен и ожидает курьера (поиск курьера) */
    DELIVERY_PENDING,
    /** Курьер идет забирать заказ */
    DELIVERY_PICKING,
    /** Курьер доставляет заказ */
    DELIVERY_DELIVERING,
    /** Доставка заказа успешно завершена */
    DELIVERY_COMPLETE,
    /** Доставка отменена */
    DELIVERY_DENIED,
    /** Возврат доставки */
    DELIVERY_REFUNDED
}
