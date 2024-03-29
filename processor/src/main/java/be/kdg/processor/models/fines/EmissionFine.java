package be.kdg.processor.models.fines;

import be.kdg.processor.models.cameras.CameraMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Simple POJO that stores information about an emission fines.
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
@DiscriminatorValue(value = "EMISSION")
public class EmissionFine extends Fine {
    private int vehicleEuroNorm;
    private int legalEuroNorm;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "emission_message")
    private CameraMessage emmisionMessage;

    public EmissionFine(FineType fineType, double amount, int carEuroNorm, int legalEuroNorm, CameraMessage emmisionMessage) {
        super(amount, fineType);
        this.vehicleEuroNorm = carEuroNorm;
        this.legalEuroNorm = legalEuroNorm;
        this.emmisionMessage = emmisionMessage;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("Vehicle has Euro norm of %d, legal Euro norm was %d.",
                vehicleEuroNorm, legalEuroNorm);
    }
}
