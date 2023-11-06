package ru.liga.common.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.common.entities.Restaurant;
import ru.liga.common.statuses.RestaurantStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findFirstById(long id);

    List<Restaurant> findAllByStatus(RestaurantStatus status);
}
