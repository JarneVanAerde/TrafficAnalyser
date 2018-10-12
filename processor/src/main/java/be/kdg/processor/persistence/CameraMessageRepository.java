package be.kdg.processor.persistence;

import be.kdg.processor.models.cameras.CameraMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CameraMessageRepository extends JpaRepository<CameraMessage, Integer> {
    public List<CameraMessage> findAllByLicensePlate(String plateId);
}
