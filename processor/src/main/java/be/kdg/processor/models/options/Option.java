package be.kdg.processor.models.options;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "options")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int optionId;
    @Setter
    private int emissionFactor;
    @Setter
    private int speedFactor;

    public Option() {
        emissionFactor = 100;
        speedFactor = 2;
    }
}
