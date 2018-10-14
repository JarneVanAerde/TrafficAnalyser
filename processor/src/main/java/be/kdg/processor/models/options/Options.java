package be.kdg.processor.models.options;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;

import javax.persistence.*;

@Configuration
@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "options")
public class Options {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int optionId;
    private int emissionFactor = 100;
}
