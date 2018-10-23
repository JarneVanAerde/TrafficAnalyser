package be.kdg.simulator.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Config class is used for the configuration
 * of the frequencies that will be used to mock
 * real traffic conditions.
 */
@Configuration
@Getter
public class FrequencyConfig {
    @Value("${scheduler.morningHours}")
    private String morningHours;
    @Value("${scheduler.eveningHours}")
    private String eveningHours;
    @Value("${scheduler.normalrate}")
    private long normalRate;
    @Value("${scheduler.peakrate}")
    private long peakRate;
}
