package be.kdg.processor.models.fines;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class EmmissionFine extends Fine {
    private String ownerEuroStandard;
    private String legalEuroStandard;

    public EmmissionFine(FineType fineType, String fineOwner, double amount, String ownerEuroNorm, String legalEuroNorm) {
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
