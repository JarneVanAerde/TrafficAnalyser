package be.kdg.processor.models.cars;

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
public class CarOwner {
    private String nationalNumber;
    private List<Car> cars;

    public CarOwner(String nationalNumber) {
        this.nationalNumber = nationalNumber;
        this.cars = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Person with national number %s has %d cars\n", nationalNumber, cars.size()));
        cars.forEach(car -> builder.append(car).append("\n"));
        return builder.toString();
    }
}
