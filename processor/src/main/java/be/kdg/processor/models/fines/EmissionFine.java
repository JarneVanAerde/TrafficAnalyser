package be.kdg.processor.models.fines;

import be.kdg.processor.models.cameras.CameraMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Simple POJO that stores information about an emission fines.
 */
@Getter
@EqualsAndHashCode
@Entity
public class EmissionFine extends Fine {
    private int carEuroNorm;
    private int legalEuroNorm;
    @OneToOne
    @JoinColumn(name = "id")
    private CameraMessage emmisionMessage;

    public EmissionFine(FineType fineType, double amount, int ownerEuroNorm, int legalEuroNorm, CameraMessage emmisionMessage) {
        super(amount, fineType);
        this.carEuroNorm = ownerEuroNorm;
        this.legalEuroNorm = legalEuroNorm;
        this.emmisionMessage = emmisionMessage;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("Vehicle has Euro norm of %d, legal Euro norm was %d.",
                carEuroNorm, legalEuroNorm);
    }
}
