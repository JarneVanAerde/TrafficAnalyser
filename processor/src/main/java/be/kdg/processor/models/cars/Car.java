package be.kdg.processor.models.cars;

import be.kdg.processor.models.fines.Fine;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple POJO that gives us information about a specific car.
 */
@Getter
@EqualsAndHashCode
public class Car {
    private String licensePlate;
    private int euroNorm;
    private final List<Fine> fines;

    public Car(String licensePlate, int euroNorm) {
        this.licensePlate = licensePlate;
        this.euroNorm = euroNorm;
        this.fines = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("Car with license plate %s and Euro standard %d",
                licensePlate, euroNorm);
    }
}
