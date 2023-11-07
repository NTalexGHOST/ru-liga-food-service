package ru.liga.common.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.common.entities.MenuItem;
import ru.liga.common.entities.Restaurant;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, UUID> {

    Optional<MenuItem> findFirstById(UUID id);

    List<MenuItem> findAllByRestaurant(Restaurant restaurant);
}
