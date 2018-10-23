package be.kdg.processor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * configuration that is used to pause and stop the queue
 * based on the id that comes from applicaton.properties
 */
@Configuration
public class QueueConfig {
    @Value("${queue.id}")
    private String queueId;

    public String getQueueId() {
        return queueId;
    }
}
