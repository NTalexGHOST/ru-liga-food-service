package ru.liga.common.mappers;

import org.mapstruct.Mapper;
import ru.liga.common.dtos.DeliveryCustomerDTO;
import ru.liga.common.entities.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    DeliveryCustomerDTO customerToDeliveryCustomerDTO(Customer customer);
}
