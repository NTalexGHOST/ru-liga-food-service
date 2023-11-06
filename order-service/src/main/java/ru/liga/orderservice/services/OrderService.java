package ru.liga.orderservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import ru.liga.common.dtos.OrderStatusDTO;
import ru.liga.common.dtos.FullOrderDTO;
import ru.liga.common.entities.*;
import ru.liga.common.exceptions.*;
import ru.liga.common.mappers.OrderMapper;
import ru.liga.common.repos.*;
import ru.liga.common.responses.CodeResponse;
import ru.liga.common.statuses.OrderStatus;
import ru.liga.common.statuses.RestaurantStatus;
import ru.liga.orderservice.responses.ConfirmOrderResponse;
import ru.liga.orderservice.responses.CustomerOrdersResponse;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = "ru.liga.common")
public class OrderService {

    private final CustomerOrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final MenuItemRepository menuItemRepo;
    private final RestaurantRepository restaurantRepo;
    private final CustomerRepository customerRepo;

    //  Временно, в 6 задании появится взаимодействие с сервисом доставки для поиска курьера
    private final CourierRepository courierRepo;

    private final OrderMapper orderMapper;

    public CustomerOrdersResponse getAllOrders() {

        List<CustomerOrder> orderEntities = orderRepo.findAll();
        if (orderEntities.isEmpty()) throw new NoOrdersException("В базе данных нет записей ни об одном заказе");

        List<FullOrderDTO> fullOrderDTOs = orderMapper.ordersToOrderDTOs(orderEntities);

        return new CustomerOrdersResponse(fullOrderDTOs, 0, 10);
    }

    public CustomerOrdersResponse getAllOrdersByCustomer() {

        //  Временная заглушка, пользователь позже будет подкручиваться из Spring Security
        Customer customer;
        Optional<Customer> optionalCustomer = customerRepo.findFirstById(1);
        customer = optionalCustomer.get();

        List<CustomerOrder> orderEntities = orderRepo.findAllByCustomer(customer);
        if (orderEntities.isEmpty()) throw new NoOrdersException("В базе данных нет записей ни об одном заказе");

        List<FullOrderDTO> fullOrderDTOs = orderMapper.ordersToOrderDTOs(orderEntities);

        return new CustomerOrdersResponse(fullOrderDTOs, 0, 10);
    }

    public FullOrderDTO getOrderById(long id) {

        CustomerOrder order;
        Optional<CustomerOrder> optionalOrder = orderRepo.findFirstById(id);
        if (optionalOrder.isPresent()) order = optionalOrder.get();
        else throw new OrderNotFoundException("Заказ с идентификатором " + id + " не найден");

        return orderMapper.orderToOrderDTO(order);
    }

    public CodeResponse changeOrderStatus(long id, OrderStatusDTO statusDTO) {

        CustomerOrder order;
        Optional<CustomerOrder> optionalOrder = orderRepo.findFirstById(id);
        if (optionalOrder.isPresent()) order = optionalOrder.get();
        else throw new OrderNotFoundException("Заказ с идентификатором " + id + " не найден");

        OrderStatus status = statusDTO.getStatus();
        order.setStatus(status);

        return new CodeResponse("200 OK", "Статус заказа успешно изменен на " + status);
    }

    public CodeResponse createOrder(long restaurantId) {

        CustomerOrder order = new CustomerOrder();

        //  Временная заглушка, пользователь позже будет подкручиваться из Spring Security
        Customer customer;
        Optional<Customer> optionalCustomer = customerRepo.findFirstById(1);
        customer = optionalCustomer.get();
        order.setCustomer(customer);

        order.setStatus(OrderStatus.CUSTOMER_CART);
        order.setTimestamp(new Timestamp(new Date().getTime()));

        Restaurant restaurant;
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findFirstById(restaurantId);
        if (optionalRestaurant.isPresent()) restaurant = optionalRestaurant.get();
        else throw new RestaurantNotFoundException("Ресторан с идентификатором " + restaurantId + " не найден");
        if (restaurant.getStatus().equals(RestaurantStatus.CLOSED))
            throw new RestaurantClosedException("Ресторан с идентификатором " + restaurantId + " не работает");
        order.setRestaurant(restaurant);

        orderRepo.save(order);

        return new CodeResponse("200 OK", "Заказ успешно создан для добавления позиций в корзину");
    }

    public ConfirmOrderResponse confirmOrder(long id) {

        CustomerOrder order;
        Optional<CustomerOrder> optionalOrder = orderRepo.findFirstById(id);
        if (optionalOrder.isPresent()) order = optionalOrder.get();
        else throw new OrderNotFoundException("Заказ с идентификатором " + id + " не найден");

        List<OrderItem> orderItems = orderItemRepo.findAllByOrder(order);
        if (orderItems.isEmpty()) throw new NoOrderItemsException("В данном заказе нет ни одной позиции");

        order.setStatus(OrderStatus.CUSTOMER_CREATED);

        //  LocalTime лишь в качестве заглушки, понимаю, что в идеале использовать какой-нибудь TimeStamp с часовым
        //  поясом и после парсить в часы и минуты для ответа.
        //  При этом можно задать какой-либо коэффициент в зависимости от расстояния курьера и покупателя до ресторана,
        //  ну и плюс к этому какое-нибудь среднее время приготовления заказа
        LocalTime estimatedTime = LocalTime.now().plusMinutes(30);

        return new ConfirmOrderResponse(id, "Здесь должен быть URL для оплаты))",
                estimatedTime.getHour() + ":" + estimatedTime.getMinute());
    }
}
