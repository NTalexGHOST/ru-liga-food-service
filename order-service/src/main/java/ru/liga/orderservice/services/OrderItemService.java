package ru.liga.orderservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@ComponentScan(basePackages = "ru.liga.common")
public class OrderItemService {

}
