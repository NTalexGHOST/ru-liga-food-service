package ru.liga.orderservice.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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
import ru.liga.common.responses.DeliveryOrdersResponse;
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

    private final ObjectMapper objectMapper;
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

    public ResponseEntity<DeliveryOrdersResponse> getAllOrdersForDelivery() {

        return ResponseEntity.ok(new DeliveryOrdersResponse());
    }

    public FullOrderDTO getOrderById(UUID id) {

        CustomerOrder order;
        Optional<CustomerOrder> optionalOrder = orderRepo.findFirstById(id);
        if (optionalOrder.isPresent()) order = optionalOrder.get();
        else throw new OrderNotFoundException("Заказ с идентификатором " + id + " не найден");

        return orderMapper.orderToOrderDTO(order);
    }

    public ResponseEntity<String> getOrderStatus(UUID orderId) {

        CustomerOrder order;
        Optional<CustomerOrder> optionalOrder = orderRepo.findFirstById(orderId);
        if (optionalOrder.isPresent()) order = optionalOrder.get();
        else return new ResponseEntity("Заказ [" + orderId + "] не найден", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(order.getStatus().toString());
    }

    /**
     * Метод изменяет статус заказа {@link OrderStatus}
     * @param orderId - идентификатор заказа
     * @param orderStatus - новый статус заказа
     * @return возвращает информацию об успешности смены статуса
     */
    public ResponseEntity<String> changeOrderStatus(UUID orderId, OrderStatus orderStatus) {

        CustomerOrder order;
        Optional<CustomerOrder> optionalOrder = orderRepo.findFirstById(orderId);
        if (optionalOrder.isPresent()) order = optionalOrder.get();
        else return new ResponseEntity("Заказ [" + orderId + "] не найден", HttpStatus.NOT_FOUND);

        //  Установка статуса заказа и его сохранение
        order.setStatus(orderStatus);
        orderRepo.save(order);

        String responseString = "Статус заказа [" + orderId + "] успешно изменен на " + orderStatus;
        System.out.println(responseString); logger.info(responseString);

        return ResponseEntity.ok(responseString);
    }

    /**
     * Метод отвечает за оплату только созданного заказа
     * @param orderId - идентификатор заказа
     * @return возвращает информацию об успешности оплаты заказа
     */
    @SneakyThrows
    public ResponseEntity<String> payForOrder(UUID orderId) {

        String responseString;

        //  Проверка на повторную оплату заказа
        OrderStatus orderStatus = OrderStatus.valueOf(getOrderStatus(orderId).getBody());
        switch (orderStatus) {
            case CUSTOMER_CREATED: break;
            case CUSTOMER_CANCELLED:
                responseString = "Заказ [" + orderId + "] отменен";
                System.out.println(responseString); logger.error(responseString);
                return new ResponseEntity(responseString, HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                responseString = "Заказ [" + orderId + "] уже оплачен";
                System.out.println(responseString); logger.warn(responseString);
                return new ResponseEntity(responseString, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        //  Проведение оплаты заказа, по сути заглушка платежной системы
        ResponseEntity response = changeOrderStatus(orderId, OrderStatus.CUSTOMER_PAID);
        if (!response.getStatusCode().is2xxSuccessful()) return response;

        //  Отправление оповещения о новом заказе в ресторан
        String message = objectMapper.writeValueAsString(getOrderById(orderId));
        rabbit.sendMessage(message, "restaurants");

        responseString = "Заказ [" + orderId + "] успешно оплачен";
        System.out.println(responseString); logger.info(responseString);

        return ResponseEntity.ok(responseString);
    }

    /**
     * Метод отвечает за отмену только созданного или уже оплаченного заказа
     * @param orderId - идентификатор заказа
     * @return возвращает информацию об успешности отмены заказа
     */
    public ResponseEntity<String> cancelOrder(UUID orderId) {

        String responseString;

        OrderStatus orderStatus = OrderStatus.valueOf(getOrderStatus(orderId).getBody());
        switch (orderStatus) {
            case CUSTOMER_CREATED:
                break;
            case CUSTOMER_PAID:
                break;
            case CUSTOMER_CANCELLED:
                responseString = "Заказ [" + orderId + "] уже отменен";
                System.out.println(responseString); logger.warn(responseString);
                return new ResponseEntity(responseString, HttpStatus.INTERNAL_SERVER_ERROR);
            default:
                responseString = "Заказ [" + orderId + "] уже нельзя отменить";
                System.out.println(responseString); logger.error(responseString);
                return new ResponseEntity(responseString, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ResponseEntity response = changeOrderStatus(orderId, OrderStatus.CUSTOMER_CANCELLED);
        if (!response.getStatusCode().is2xxSuccessful()) return response;

        responseString = "Заказ [" + orderId + "] успешно отменен";
        System.out.println(responseString); logger.info(responseString);

        return ResponseEntity.ok(responseString);
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
        orderRepo.saveAndFlush(order);


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
            orderItem.setId(orderItemId);

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
        String estimatedTimeString;
        if (estimatedTime.getMinute() < 10) estimatedTimeString = estimatedTime.getHour() + ":0" + estimatedTime.getMinute();
        else estimatedTimeString = estimatedTime.getHour() + ":" + estimatedTime.getMinute();

        return new CreateOrderResponse(orderId, "*** URL для оплаты ***",
                    estimatedTimeString);
    }
}
