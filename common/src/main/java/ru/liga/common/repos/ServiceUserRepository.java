package ru.liga.common.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.liga.common.entities.ServiceUser;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceUserRepository extends JpaRepository<ServiceUser, UUID> {
    Optional<ServiceUser> findFirstById(UUID id);
}
