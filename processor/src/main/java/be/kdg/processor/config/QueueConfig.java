package be.kdg.processor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueConfig {
    @Value("${queue.id}")
    private String queueId;

    public String getQueueId() {
        return queueId;
    }
}
