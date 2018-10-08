package be.kdg.processor.models.fines;

import be.kdg.processor.models.cameras.CameraMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * Simple POJO that stores information about an speed fines.
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@Entity
@DiscriminatorValue(value = "speed")
public class SpeedFine extends Fine {
    private double carSpeed;
    private double legalSpeed;
    @OneToOne(cascade = CascadeType.ALL)
    private CameraMessage enterCamera;
    @OneToOne(cascade = CascadeType.ALL)
    private CameraMessage exitCamera;

    public SpeedFine(FineType fineType, double amount, double carSpeed, double legalSpeed, CameraMessage enterCamera, CameraMessage exitCamera) {
        super(amount, fineType);
        this.carSpeed = carSpeed;
        this.legalSpeed = legalSpeed;
        this.enterCamera = enterCamera;
        this.exitCamera = exitCamera;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("Owner drove %.1f/h, legal speedLimit was %.1f/h.",
                carSpeed, legalSpeed);
    }
}
