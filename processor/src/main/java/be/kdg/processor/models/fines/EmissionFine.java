package be.kdg.processor.models.fines;

import be.kdg.processor.models.cameras.CameraMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Simple POJO that stores information about an emission fines.
 */
@Getter
@EqualsAndHashCode
public class EmissionFine extends Fine {
    private int carEuroNorm;
    private int legalEuroNorm;
    private CameraMessage emmisionMessage;

    public EmissionFine(int fineId, FineType fineType, double amount, int ownerEuroNorm, int legalEuroNorm, CameraMessage emmisionMessage) {
        super(fineId, amount, fineType);
        this.carEuroNorm = ownerEuroNorm;
        this.legalEuroNorm = legalEuroNorm;
        this.emmisionMessage = emmisionMessage;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("Car has Euro norm of %d, legal Euro norm was %d.",
                carEuroNorm, legalEuroNorm);
    }
}
