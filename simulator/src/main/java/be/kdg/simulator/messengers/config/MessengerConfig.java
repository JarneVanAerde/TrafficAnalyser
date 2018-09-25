package be.kdg.simulator.messengers.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessengerConfig {
    @Value("${messaging.queue.name}")
    private String QUEUE_NAME;

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }
}
