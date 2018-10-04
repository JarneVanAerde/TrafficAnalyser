package be.kdg.processor.models.fines;

import be.kdg.processor.models.cameras.CameraMessage;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Simple POJO that stores information about an speed fines.
 */
@Getter
@EqualsAndHashCode
public class SpeedFine extends Fine {
    private double carSpeed;
    private double legalSpeed;
    private CameraMessage enterCamera;
    private CameraMessage exitCamera;

    public SpeedFine(int fineId, FineType fineType, double amount, double carSpeed, double legalSpeed, CameraMessage enterCamera, CameraMessage exitCamera) {
        super(fineId, fineType, amount);
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
