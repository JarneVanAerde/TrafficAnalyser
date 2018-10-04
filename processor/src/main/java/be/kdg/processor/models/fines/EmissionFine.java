package be.kdg.processor.models.fines;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Simple POJO that stores information about an emission fines.
 */
@Getter
@EqualsAndHashCode
public class EmissionFine extends Fine {
    private String ownerEuroStandard;
    private String legalEuroStandard;

    public EmissionFine(FineType fineType, String fineOwner, double amount, String ownerEuroNorm, String legalEuroNorm) {
        super(fineType, fineOwner, amount);
        this.ownerEuroStandard = ownerEuroNorm;
        this.legalEuroStandard = legalEuroNorm;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" Owner has Euro standard of %s, legal Euro standard was %s.",
                ownerEuroStandard, legalEuroStandard);
    }
}
