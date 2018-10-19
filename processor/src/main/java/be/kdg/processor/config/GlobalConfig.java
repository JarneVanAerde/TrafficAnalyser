package be.kdg.processor.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Configuration that is used throughout the application.
 */
@Configuration
@EnableScheduling
@EnableCaching
public class GlobalConfig {
}
