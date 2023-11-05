package ru.liga.common.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.common.entities.Courier;
import ru.liga.common.statuses.CourierStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourierRepository extends JpaRepository<Courier, Long> {
    Optional<Courier> findFirstById(long id);

    List<Courier> findAllByStatus(CourierStatus status);
}
