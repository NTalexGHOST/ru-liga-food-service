package ru.liga.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.liga.common.responses.CodeResponse;
import ru.liga.common.statuses.CourierStatus;

@FeignClient(name = "delivery-service", url = "http://localhost:8081")
public interface DeliveryFeign {

    @PutMapping("/courier/{id}")
    CodeResponse changeCourierStatus(@PathVariable("id") Long id, @RequestParam("status") CourierStatus status);
}
