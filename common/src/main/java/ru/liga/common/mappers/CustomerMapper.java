package ru.liga.common.mappers;

import org.mapstruct.Mapper;
import ru.liga.common.dtos.DeliveryCustomerDTO;
import ru.liga.common.entities.Customer;

/**
 * Маппер, отвечающий за любые преобразования в сервисе доставки
 */
@Mapper(componentModel = "spring")
public interface CustomerMapper {

    /**
     * Преобразовывает сущность {@link Customer} в соответствующую {@link DeliveryCustomerDTO}
     * @param customer - сущность покупателя
     * @return возвращает {@link DeliveryCustomerDTO}
     */
    DeliveryCustomerDTO customerToDeliveryCustomerDTO(Customer customer);
}
