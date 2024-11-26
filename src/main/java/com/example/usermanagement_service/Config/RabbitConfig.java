package com.example.usermanagement_service.Config;

import com.example.usermanagement_service.service.UserService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.MessageConverter; // Use the correct MessageConverter from AMQP

@Configuration
@EnableRabbit
public class RabbitConfig {

    // Define the Jackson2JsonMessageConverter to convert JSON to Java objects (e.g., UserDTO)
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(); // Use the AMQP-compatible Jackson2JsonMessageConverter
    }

    // Define the MessageListenerAdapter and apply the message converter to it
    @Bean
    public MessageListenerAdapter messageListenerAdapter(UserService userService) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(userService, "receivePassengerCredentials");
        adapter.setMessageConverter(messageConverter()); // Set the AMQP-compatible message converter here
        return adapter;
    }

    // Define the listener container, but we don't set the converter here
    @Bean
    public MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setMessageListener(messageListenerAdapter); // Assign the message listener adapter
        return container;
    }

    // Define the Queue you are consuming from (optional, but good for clarity)
    @Bean
    public Queue passengerQueue() {
        return new Queue("passengerQueue", true); // Declare a queue with persistence enabled
    }
}
