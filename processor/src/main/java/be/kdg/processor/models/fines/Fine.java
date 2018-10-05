package be.kdg.processor.models.fines;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

/**
 * Abstract super class used by al specific fines for inheritance.
 */
@EqualsAndHashCode
@Getter
@Entity
@Table(name = "fines")
public class Fine {
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
