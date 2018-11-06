package be.kdg.processor.config;

import be.kdg.sa.services.CameraServiceProxy;
import be.kdg.sa.services.LicensePlateServiceProxy;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class is used to make beans of the external services.
 */
@Configuration
@EnableCaching
public class ExternalServiceConfig {
    @Bean
    public CameraServiceProxy cameraServiceProxy() {
        return new CameraServiceProxy();
    }

    @Bean
    public LicensePlateServiceProxy licensePlateServiceProxy() {
        return new LicensePlateServiceProxy();
    }
}
