package be.kdg.simulator.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class GeneratorConfig {
    @Value("${generator.message.maxid}")
    private int maxId;
}
