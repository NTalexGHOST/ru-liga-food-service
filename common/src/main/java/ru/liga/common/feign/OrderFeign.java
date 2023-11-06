package ru.liga.common.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ru.liga.common.dtos.OrderStatusDTO;
import ru.liga.common.responses.CodeResponse;

@FeignClient(name = "order-service", url = "http://localhost:8084")
public interface OrderFeign {

    @PutMapping("/order/{id}/status")
    CodeResponse changeOrderStatus(@PathVariable("id") Long id, @RequestBody OrderStatusDTO statusDTO);
}
