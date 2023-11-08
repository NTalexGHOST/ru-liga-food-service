package ru.liga.kitchenservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import ru.liga.common.dtos.FullOrderDTO;

@Service
@RequiredArgsConstructor
public class QueueListener {

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @RabbitListener(queues = "kitchen-service")
    public void processMyQueue(String message) {

        //  Преобразование полученного сообщения в необходимую конструкцию FullOrderDTO
        FullOrderDTO orderDTO = objectMapper.readValue(message, FullOrderDTO.class);

        //  Формирование оповещения (можно сказать чека) о поступлении нового заказа в ресторан
        StringBuilder outputStringBuilder = new StringBuilder();
        outputStringBuilder.append("[" + orderDTO.getTimestamp() + "] Получен новый заказ [" + orderDTO.getId() + "]\n");
        orderDTO.getItems().stream().forEach(orderItemDTO -> outputStringBuilder.append("\t" + orderItemDTO.getName() + "\t|\t" + orderItemDTO.getQuantity() + "x\t|\t" + orderItemDTO.getPrice() + "р.\n"));
        System.out.println(outputStringBuilder);
    }
}
