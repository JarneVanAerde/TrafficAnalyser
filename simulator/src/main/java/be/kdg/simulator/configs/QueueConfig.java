package be.kdg.simulator.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Used for the configuration of the queue.
 * The name of the queue is injected from application.properties.
 */
@Configuration
public class QueueConfig {
    @Value("${messaging.queue.name}")
    private String queueName;

    @Bean
    public Queue queue() {
        return new Queue(queueName);
    }
}
