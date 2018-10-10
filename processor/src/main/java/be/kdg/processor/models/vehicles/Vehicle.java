package be.kdg.processor.models.vehicles;

import be.kdg.processor.models.fines.Fine;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Simple POJO that gives us information about a specific car.
 */
@EqualsAndHashCode
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    private String licensePlate;
    private int euroNorm;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_id")
    private final List<Fine> fines;

    public Vehicle(String licensePlate, int euroNorm) {
        this.licensePlate = licensePlate;
        this.euroNorm = euroNorm;
        this.fines = new ArrayList<>();
    }

    public Vehicle() {
        fines = new ArrayList<>();
    }

    public List<Fine> getFines() {
        return Collections.unmodifiableList(fines);
    }

    public void addFine(Fine fine) {
        fines.add(fine);
    }

    @Override
    public String toString() {
        return String.format("Vehicle with license plate %s and Euro standard %d",
                licensePlate, euroNorm);
    }
}
