package be.kdg.processor.persistence;

import be.kdg.processor.models.options.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionsRepository extends JpaRepository<Option, Integer> {
}
