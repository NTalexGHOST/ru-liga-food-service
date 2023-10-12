package ru.liga.OrderService.dto;

public enum OrderStatus {
    //  Есть ли смысл делать возможной отмену заказа (CANCELLED) пользователем
    //  или по нашему заданию этого не предусмотрено?
    CART, CANCELLED, REJECTED, PAID, PREPARING, COURIER, DELIVERED
}
