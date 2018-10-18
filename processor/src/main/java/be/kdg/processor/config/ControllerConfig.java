package be.kdg.processor.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This config class is used for the configuration of a model mapper.
 * A model mapper is needed to map a model to a DTO.
 */
@Configuration
public class ControllerConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
