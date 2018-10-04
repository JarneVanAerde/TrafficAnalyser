package be.kdg.processor.models.cars;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * Simple POJO that gives us information about a specific car.
 */
@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Car {
    private String licensePlate;
    private int euroNorm;

    @Override
    public String toString() {
        return String.format("Car with license plate %s and Euro standard %d",
                licensePlate, euroNorm);
    }
}
