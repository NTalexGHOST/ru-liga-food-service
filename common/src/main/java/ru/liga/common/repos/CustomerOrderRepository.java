package ru.liga.common.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.common.entities.Customer;
import ru.liga.common.entities.CustomerOrder;
import ru.liga.common.entities.Restaurant;
import ru.liga.common.statuses.OrderStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {

    Optional<CustomerOrder> findFirstById(long id);

    List<CustomerOrder> findAll();
    List<CustomerOrder> findAllByStatus(OrderStatus status);
    List<CustomerOrder> findAllByCustomer(Customer customer);
    List<CustomerOrder> findAllByCustomerAndStatus(Customer customer, OrderStatus status);
    List<CustomerOrder> findAllByRestaurantAndStatus(Restaurant restaurant, OrderStatus status);
}
