package be.kdg.processor.models.fines;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode

public class SpeedFine extends Fine {
    private int ownerSpeed;
    private int legalSpeed;

    public SpeedFine(FineType fineType, String fineOwner, double amount, int ownerSpeed, int legalSpeed) {
        super(fineType, fineOwner, amount);
        this.ownerSpeed = ownerSpeed;
        this.legalSpeed = legalSpeed;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" Owner drove %d/h, legal speed was %d/h.",
                ownerSpeed, legalSpeed);
    }
}
