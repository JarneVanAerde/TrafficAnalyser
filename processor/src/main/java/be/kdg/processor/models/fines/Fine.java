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
    private final FineType fineType;
    private String fineOwner;
    private double amount;

    @Override
    public String toString() {
        return String.format("%s for %s with an amount of %.2f.",
                fineType, fineOwner, amount);
    }
}
