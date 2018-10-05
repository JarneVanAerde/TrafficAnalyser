package be.kdg.processor.models.fines;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Abstract super class used by al specific fines for inheritance.
 */
@EqualsAndHashCode
@Getter
public abstract class Fine {
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
