package be.kdg.processor.models.fines;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Abstract super class used by al specific fines for inheritance.
 */
@EqualsAndHashCode
@Getter
@NoArgsConstructor
@Entity
@Table(name = "fines")
@DiscriminatorColumn(name = "type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Fine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int fineId;
    private double amount;
    private FineType fineType;

    public Fine(double amount, FineType fineType) {
        this.amount = amount;
        this.fineType = fineType;
    }

    @Override
    public String toString() {
        return String.format("%s (%d) with an amount of %.2f.",
                fineType, fineId, amount);
    }
}
