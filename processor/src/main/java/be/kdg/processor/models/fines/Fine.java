package be.kdg.processor.models.fines;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract super class used by al specific fines for inheritance.
 */
@EqualsAndHashCode
@Getter
@Entity
@Table(name = "fines")
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int fineId;
    @Setter
    private double amount;
    private FineType fineType;
    private LocalDateTime creationDate;
    @Setter
    private String changeAmountMotivation;
    @Setter
    private boolean approved;

    public Fine(double amount, FineType fineType) {
        this.amount = amount;
        this.fineType = fineType;
        this.creationDate = LocalDateTime.now();
        this.approved = false;
        this.changeAmountMotivation = "Default";
    }

    public Fine() {
        this.approved = false;
    }

    @Override
    public String toString() {
        return String.format("%s (%d) with an amount of %.2f.",
                fineType, fineId, amount);
    }
}
