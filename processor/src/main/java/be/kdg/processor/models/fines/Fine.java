package be.kdg.processor.models.fines;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Abstract super class used by al specific fines for inheritance.
 */
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public abstract class Fine {
    private int fineId;
    private double amount;
    private FineType fineType;

    @Override
    public String toString() {
        return String.format("%s (%dà with an amount of %.2f.",
                fineType, fineId, amount);
    }
}
