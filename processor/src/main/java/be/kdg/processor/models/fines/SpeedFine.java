package be.kdg.processor.models.fines;

import be.kdg.processor.models.cameras.CameraMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Simple POJO that stores information about an speed fines.
 */
@Getter
@EqualsAndHashCode
@Entity
public class SpeedFine extends Fine {
    private double carSpeed;
    private double legalSpeed;
    @OneToOne
    @JoinColumn(name = "enter_id")
    private CameraMessage enterCamera;
    @OneToOne
    @JoinColumn(name = "exit_id")
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
