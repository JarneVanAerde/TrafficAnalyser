package be.kdg.processor.persistence;

import be.kdg.processor.models.vehicles.VehicleOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleOwnerRepository extends JpaRepository<VehicleOwner, Integer> {
}
