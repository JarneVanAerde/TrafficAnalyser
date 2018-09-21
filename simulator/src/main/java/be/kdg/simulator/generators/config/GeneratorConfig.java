package be.kdg.simulator.generators.config;

import be.kdg.simulator.generators.FileGenerator;
import be.kdg.simulator.generators.MessageGenerator;
import be.kdg.simulator.generators.RandomMessageGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
public class GeneratorConfig {
    /*@Bean
    //@ConditionalOnProperty(name="load", havingValue = "file")
    public MessageGenerator newFileGenerator() {
        return new FileGenerator();
    }

    @Bean
    //@ConditionalOnProperty(name="load", havingValue = "random")
    public MessageGenerator newMessageGenerator() {
        return new RandomMessageGenerator();
    }*/
}
