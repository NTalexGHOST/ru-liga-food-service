package ru.liga.orderservice.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ru.liga.common.dtos.FullOrderDTO;
import ru.liga.common.entities.*;
import ru.liga.common.exceptions.*;
import ru.liga.common.mappers.OrderMapper;
import ru.liga.common.repos.*;
import ru.liga.common.statuses.OrderStatus;
import ru.liga.common.statuses.RestaurantStatus;
import ru.liga.orderservice.dtos.CreateOrderDTO;
import ru.liga.orderservice.producer.RabbitMQProducerServiceImpl;
import ru.liga.orderservice.responses.CreateOrderResponse;
import ru.liga.orderservice.responses.CustomerOrdersResponse;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service @Slf4j
@RequiredArgsConstructor
@ComponentScan(basePackages = "ru.liga.common")
public class OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final CustomerOrderRepository orderRepo;
    private final OrderItemRepository orderItemRepo;
    private final RestaurantRepository restaurantRepo;
    private final CustomerRepository customerRepo;
    private final MenuItemRepository menuItemRepo;

    private final OrderMapper orderMapper;
    private final RabbitMQProducerServiceImpl rabbit;

    public ResponseEntity<CustomerOrdersResponse> getAllOrdersByCustomer() {

        //  Временная заглушка, пользователь позже будет подкручиваться из Spring Security
        Customer customer; UUID customerId = UUID.fromString("62dcd09a-7c76-4c82-8443-a9a0021171d5");
        Optional<Customer> optionalCustomer = customerRepo.findFirstById(customerId);
        customer = optionalCustomer.get();

        //  Поиск всех заказов покупателя и преобразование полученного списка в необходимый DTO
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

    public ResponseEntity<String> changeOrderStatus(UUID orderId, OrderStatus orderStatus) {

        CustomerOrder order;
        Optional<CustomerOrder> optionalOrder = orderRepo.findFirstById(orderId);
        if (optionalOrder.isPresent()) order = optionalOrder.get();
        else return new ResponseEntity("Заказ с идентификатором " + orderId + " не найден", HttpStatus.NOT_FOUND);
        order.setStatus(orderStatus);

        return ResponseEntity.ok("Статус заказа с идентификатором " + orderId + " успешно изменен на " + orderStatus);
    }

    public CreateOrderResponse createOrder(CreateOrderDTO orderDTO) {

        CustomerOrder order = new CustomerOrder();

        //  Конструкция на случай совпадения UUID с существующим заказом
        Optional<CustomerOrder> optionalOrder; UUID orderId;
        do {
            orderId = UUID.randomUUID();
            optionalOrder = orderRepo.findFirstById(orderId);
        } while (optionalOrder.isPresent());
        order.setId(orderId);

        // Получение UUID ресторана и поиск по нему в соответствующем репозитории
        Restaurant restaurant; UUID restaurantId = orderDTO.getRestaurantId();
        Optional<Restaurant> optionalRestaurant = restaurantRepo.findFirstById(restaurantId);

        //  Проверка на существование указанного ресторана и открыт ли он в данный момент
        if (optionalRestaurant.isPresent()) restaurant = optionalRestaurant.get();
        else throw new RestaurantNotFoundException("Ресторан с идентификатором " + restaurantId + " не найден");
        if (restaurant.getStatus().equals(RestaurantStatus.CLOSED))
            throw new RestaurantClosedException("Ресторан с идентификатором " + restaurantId + " не работает");
        order.setRestaurant(restaurant);

        //  Временная заглушка, пользователь позже будет подкручиваться из Spring Security
        Customer customer; UUID customerId = UUID.fromString("62dcd09a-7c76-4c82-8443-a9a0021171d5");
        Optional<Customer> optionalCustomer = customerRepo.findFirstById(customerId);
        customer = optionalCustomer.get(); order.setCustomer(customer);

        //  Проверка на существование переданных позиций
        orderDTO.getItems().stream().forEach(orderItemDTO -> {
            UUID menuItemId = orderItemDTO.getMenuItemId();
            Optional<MenuItem> optionalMenuItem = menuItemRepo.findFirstById(menuItemId);
            if (optionalMenuItem.isEmpty())
                throw new MenuItemNotFoundException("Позиция в меню с идентификатором " + menuItemId + " не найдена");
        });

        //  Установка статуса об успешном создании заказа и текущего времени
        order.setStatus(OrderStatus.CUSTOMER_CREATED);
        order.setTimestamp(new Timestamp(new Date().getTime()));

        //  Сохранение заказа в репозиторий для возможности добавления в него позиций
        orderRepo.save(order);


        //  Приступаем к созданию позиций заказа и его заполнению ими
        orderDTO.getItems().stream().forEach(orderItemDTO -> {
            MenuItem menuItem = menuItemRepo.findFirstById(orderItemDTO.getMenuItemId()).get();
            OrderItem orderItem = new OrderItem();

            //  Конструкция на случай совпадения UUID с существующей позицией заказа
            Optional<OrderItem> optionalOrderItem; UUID orderItemId;
            do {
                orderItemId = UUID.randomUUID();
                optionalOrderItem = orderItemRepo.findFirstById(orderItemId);
            } while (optionalOrderItem.isPresent());
            order.setId(orderItemId);

            orderItem.setOrder(order);
            orderItem.setMenuItem(menuItem);
            orderItem.setPrice(menuItem.getPrice());
            orderItem.setQuantity(orderItemDTO.getQuantity());

            orderItemRepo.save(orderItem);
        });
        orderRepo.save(order);

        //  LocalTime лишь в качестве заглушки, понимаю, что в идеале использовать какой-нибудь TimeStamp с часовым
        //  поясом и после парсить в часы и минуты для ответа.
        //  При этом можно задать какой-либо коэффициент в зависимости от расстояния курьера и покупателя до
        //  ресторана, ну и плюс к этому какое-нибудь среднее время приготовления заказа
        LocalTime estimatedTime = LocalTime.now().plusMinutes(30);

        return new CreateOrderResponse(orderId, "*** URL для оплаты ***",
                    estimatedTime.getHour() + ":" + estimatedTime.getMinute());
    }
}
