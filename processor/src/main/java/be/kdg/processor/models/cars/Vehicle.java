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
public class Vehicle {
    private int carId;
    private String licensePlate;
    private int euroNorm;
    private final List<Fine> fines;

    public Vehicle(int carId, String licensePlate, int euroNorm) {
        this.carId = carId;
        this.licensePlate = licensePlate;
        this.euroNorm = euroNorm;
        this.fines = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("Vehicle %d with license plate %s and Euro standard %d",
                carId, licensePlate, euroNorm);
    }
}
