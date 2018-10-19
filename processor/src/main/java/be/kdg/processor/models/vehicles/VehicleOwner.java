package be.kdg.processor.models.vehicles;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Simple POJO that gives us information about a car owner.
 * A vehicle owner can have multiple vehicles.
 */
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name = "vehicle_owners")
public class VehicleOwner {
    @Getter
    @Id
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

    public List<Vehicle> getVehicles() {
        return Collections.unmodifiableList(vehicles);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Person with national number %s has %d vehicles\n", nationalNumber, vehicles.size()));
        vehicles.forEach(vehicle -> builder.append(vehicle).append("\n"));
        return builder.toString();
    }
}
