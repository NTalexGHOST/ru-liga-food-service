package ru.liga.kitchenservice.config;

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
    public Declarables myQueue() {

        Queue couriersQueue = new Queue("couriers", false);
        Queue customersQueue = new Queue("customers", false);
        amqpAdmin().declareQueue(couriersQueue);
        amqpAdmin().declareQueue(customersQueue);

        DirectExchange directExchange = new DirectExchange("directExchange");

        return new Declarables(couriersQueue, customersQueue, directExchange,
                BindingBuilder.bind(couriersQueue).to(directExchange).with("couriers"),
                BindingBuilder.bind(customersQueue).to(directExchange).with("customers"));
    }
}