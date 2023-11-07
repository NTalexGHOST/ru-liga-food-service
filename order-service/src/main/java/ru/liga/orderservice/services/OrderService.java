package ru.liga.orderservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ru.liga.common.dtos.FullOrderDTO;
import ru.liga.common.entities.*;
import ru.liga.common.exceptions.*;
import ru.liga.common.feign.DeliveryFeign;
import ru.liga.common.mappers.OrderMapper;
import ru.liga.common.repos.*;
import ru.liga.common.statuses.OrderStatus;
import ru.liga.common.statuses.RestaurantStatus;
import ru.liga.orderservice.producer.RabbitMQProducerServiceImpl;
import ru.liga.orderservice.responses.ConfirmOrderResponse;
import ru.liga.orderservice.responses.CustomerOrdersResponse;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = "ru.liga.common")
public class OrderService {

    private final CustomerOrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final RestaurantRepository restaurantRepo;
    private final CustomerRepository customerRepo;

    private final OrderMapper orderMapper;
    private final DeliveryFeign deliveryFeign;
    private final RabbitMQProducerServiceImpl rabbit;

    public ResponseEntity<CustomerOrdersResponse> getAllOrdersByCustomer() {

        //  Временная заглушка, пользователь позже будет подкручиваться из Spring Security
        Customer customer; UUID customerId = UUID.fromString("62dcd09a-7c76-4c82-8443-a9a0021171d5");
        Optional<Customer> optionalCustomer = customerRepo.findFirstById(customerId);
        customer = optionalCustomer.get();

        List<CustomerOrder> orderEntities = orderRepo.findAllByCustomer(customer);
        if (orderEntities.isEmpty()) return new ResponseEntity(new CustomerOrdersResponse(), HttpStatus.NOT_FOUND);
        List<FullOrderDTO> fullOrderDTOs = orderMapper.ordersToOrderDTOs(orderEntities);

        return ResponseEntity.ok(new CustomerOrdersResponse(fullOrderDTOs, 0, 10));
    }

    public FullOrderDTO getOrderById(UUID id) {

        CustomerOrder order;
        Optional<CustomerOrder> optionalOrder = orderRepo.findFirstById(id);
        if (optionalOrder.isPresent()) order = optionalOrder.get();
        else throw new OrderNotFoundException("Заказ с идентификатором " + id + " не найден");

        return orderMapper.orderToOrderDTO(order);
    }

    public ResponseEntity<String> changeOrderStatus(UUID id, OrderStatus status) {

        CustomerOrder order;
        Optional<CustomerOrder> optionalOrder = orderRepo.findFirstById(id);
        if (optionalOrder.isPresent()) order = optionalOrder.get();
        else return new ResponseEntity("Заказ с идентификатором " + id + " не найден", HttpStatus.NOT_FOUND);

        order.setStatus(status);

        return ResponseEntity.ok("Статус заказа успешно изменен на " + status);
    }

    public CustomerOrdersResponse createOrder(ShortOrderDTO orderDTO) {

        CustomerOrder order = new CustomerOrder();
        Restaurant restaurant; long restaurantId = orderDTO.getRestaurantId();


            //  Временная заглушка, пользователь позже будет подкручиваться из Spring Security
            Customer customer;
            Optional<Customer> optionalCustomer = customerRepo.findFirstById(1);
            customer = optionalCustomer.get();
            order.setCustomer(customer);


            order.setStatus(OrderStatus.CUSTOMER_CREATED);
            order.setStatus(OrderStatus.CUSTOMER_CART);
            order.setTimestamp(new Timestamp(new Date().getTime()));


            Restaurant restaurant;
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
            orderRepo.save(order);

        //  Позднее заменится на поиск курьера в сервисе доставки
        //  и соответственно, здесь просто будет добавляться запись о заказе в брокер
        order.setCourier(courierRepo.findFirstById(1).get());

            CustomerOrder order;
            Optional<CustomerOrder> optionalOrder = orderRepo.findFirstById(id);
            if (optionalOrder.isPresent()) order = optionalOrder.get();
            else throw new OrderNotFoundException("Заказ с идентификатором " + id + " не найден");

            orderRepo.save(order);
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
