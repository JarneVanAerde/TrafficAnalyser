package be.kdg.processor.models.vehicles;

import be.kdg.processor.models.fines.Fine;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple POJO that gives us information about a specific car.
 */
@Getter
@EqualsAndHashCode
public class Vehicle {
    private int vehicleId;
    private String licensePlate;
    private int euroNorm;
    private final List<Fine> fines;

    public Vehicle(int carId, String licensePlate, int euroNorm) {
        this.vehicleId = carId;
        this.licensePlate = licensePlate;
        this.euroNorm = euroNorm;
        this.fines = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("Vehicle %d with license plate %s and Euro standard %d",
                vehicleId, licensePlate, euroNorm);
    }
}
