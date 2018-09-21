package be.kdg.simulator.generators;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeneratorConfig {
    @Bean
    @ConditionalOnProperty(name="load", havingValue = "file")
    public MessageGenerator newFileGenerator() {
        return new FileGenerator();
    }

    @Bean
    @ConditionalOnProperty(name="load", havingValue = "random")
    public MessageGenerator newMessageGenerator() {
        return new RandomMessageGenerator();
    }
}
