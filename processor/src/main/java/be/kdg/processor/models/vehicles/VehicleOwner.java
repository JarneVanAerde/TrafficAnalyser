package be.kdg.processor.models.vehicles;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple POJO that gives us information about a car owner.
 * A carowner can have multiple cars.
 */
@Getter
@EqualsAndHashCode
public class VehicleOwner {
    private int ownerId;
    private String nationalNumber;
    private List<Vehicle> cars;

    public VehicleOwner(int ownerId, String nationalNumber) {
        this.ownerId = ownerId;
        this.nationalNumber = nationalNumber;
        this.cars = new ArrayList<>();
    }

    public void addCar(Vehicle car) {
        cars.add(car);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Person %d with national number %s has %d cars\n", ownerId, nationalNumber, cars.size()));
        cars.forEach(car -> builder.append(car).append("\n"));
        return builder.toString();
    }
}
