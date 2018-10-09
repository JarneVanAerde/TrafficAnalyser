package be.kdg.processor.models.vehicles;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple POJO that gives us information about a car owner.
 * A carowner can have multiple vehicles.
 */
@Getter
@EqualsAndHashCode
@Entity
@Table(name = "vehicle_owners")
public class VehicleOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ownerId;
    private String nationalNumber;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    private List<Vehicle> vehicles;

    public VehicleOwner(String nationalNumber) {
        this.nationalNumber = nationalNumber;
        this.vehicles = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Person %d with national number %s has %d vehicles\n", ownerId, nationalNumber, vehicles.size()));
        vehicles.forEach(car -> builder.append(car).append("\n"));
        return builder.toString();
    }
}
