package be.kdg.processor.persistence;

import be.kdg.processor.models.fines.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface FineRepository extends JpaRepository<Fine, Integer> {
}
