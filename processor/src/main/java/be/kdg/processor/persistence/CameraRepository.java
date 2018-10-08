package be.kdg.processor.persistence;

import be.kdg.processor.models.cameras.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CameraRepository extends JpaRepository<Camera, Integer> {
}
