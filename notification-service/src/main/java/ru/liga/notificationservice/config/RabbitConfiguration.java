package ru.liga.notificationservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfiguration {

    @Bean
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory("localhost");

        cachingConnectionFactory.setUsername("guest");
        cachingConnectionFactory.setPassword("guest");

        return cachingConnectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Declarables notificationsQueue() {

        Queue customersQueue = new Queue("order-service", false);
        Queue restaurantsQueue = new Queue("kitchen-service", false);
        Queue couriersQueue = new Queue("delivery-service", false);

        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(restaurantsQueue, couriersQueue, directExchange,
                BindingBuilder.bind(restaurantsQueue).to(directExchange).with("kitchen-service"),
                BindingBuilder.bind(couriersQueue).to(directExchange).with("delivery-service"),
                BindingBuilder.bind(customersQueue).to(directExchange).with("order-service"));
    }
}
