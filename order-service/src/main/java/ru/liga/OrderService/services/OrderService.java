package ru.liga.OrderService.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import ru.liga.common.dtos.CreateOrderDTO;
import ru.liga.common.dtos.OrderDTO;
import ru.liga.common.entities.*;
import ru.liga.common.exceptions.*;
import ru.liga.common.mappers.OrderMapper;
import ru.liga.common.repos.*;
import ru.liga.common.responses.AllOrdersResponse;
import ru.liga.common.responses.CreateOrderResponse;
import ru.liga.common.statuses.OrderStatus;
import ru.liga.common.statuses.RestaurantStatus;

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

    public AllOrdersResponse getAllOrders() {

        List<CustomerOrder> orderEntities = orderRepo.findAll();
        if (orderEntities.isEmpty()) throw new NoOrdersException("В базе данных нет записей ни об одном заказе");

        List<OrderDTO> orderDTOs = orderMapper.ordersToOrderDTOs(orderEntities);
        AllOrdersResponse response = new AllOrdersResponse(orderDTOs, 0, 10);

        return response;
    }

    public OrderDTO findOrderById(long id) {

        CustomerOrder order;
        Optional<CustomerOrder> optionalOrder = orderRepo.findFirstById(id);
        if (optionalOrder.isPresent()) order = optionalOrder.get();
        else throw new OrderNotFoundException("Заказ с идентификатором " + id + " не найден");

        return orderMapper.orderToOrderDTO(order);
    }

    public CreateOrderResponse createOrder(CreateOrderDTO orderDTO) {

        CustomerOrder order = new CustomerOrder();
        Restaurant restaurant; long restaurantId = orderDTO.getRestaurantId();


        //  Временная заглушка, пользователь позже будет подкручиваться из Spring Security
        Customer customer;
        Optional<Customer> optionalCustomer = customerRepo.findFirstById(1);
        customer = optionalCustomer.get();
        order.setCustomer(customer);


        order.setStatus(OrderStatus.CUSTOMER_CREATED);
        order.setTimestamp(new Timestamp(new Date().getTime()));


        Optional<Restaurant> optionalRestaurant = restaurantRepo.findFirstById(restaurantId);
        if (optionalRestaurant.isPresent()) restaurant = optionalRestaurant.get();
        else throw new RestaurantNotFoundException("Ресторан с идентификатором " + restaurantId + " не найден");
        if (restaurant.getStatus().equals(RestaurantStatus.CLOSED))
            throw new RestaurantClosedException("Ресторан с идентификатором " + restaurantId + " не работает");

        order.setRestaurant(restaurant);
        orderRepo.saveAndFlush(order);


        //  Мне не очень нравятся примеры запросов и ответов в примере, а также представленные статусы заказа. Как по
        //  мне, будет правильнее добавить еще один статус CART и инициализировать по сути пустой заказ, а уже после
        //  отдельными запросами добавлять в него предметы и подвердить его для перехода в статус CUSTOMER_CREATED
        orderDTO.getItems().forEach(orderItemDTO -> {
            OrderItem orderItem = new OrderItem();

            MenuItem menuItem; long menuItemId = orderItemDTO.getMenuItemId();
            Optional<MenuItem> optionalMenuItem = menuItemRepo.findFirstById(menuItemId);
            if (optionalMenuItem.isPresent()) menuItem = optionalMenuItem.get();
            else throw new MenuItemNotFoundException("Позиция в меню с идентификатором " + menuItemId + " не найдена");

            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setPrice(menuItem.getPrice());
            orderItem.setQuantity(orderItemDTO.getQuantity());

            orderItemRepo.save(orderItem);
        });


        //  Позднее заменится на поиск курьера в сервисе доставки
        //  и соответственно, здесь просто будет добавляться запись о заказе в брокер
        order.setCourier(courierRepo.findFirstById(1).get());


        orderRepo.save(order);

        //  LocalTime лишь в качестве заглушки, понимаю, что в идеале использовать какой-нибудь TimeStamp
        //  с часовым поясом и после парсить в часы и минуты для ответа
        return new CreateOrderResponse(order.getId(), "Здесь должен быть URL для оплаты))",
                LocalTime.now().plusMinutes(30).toString());
    }
}
