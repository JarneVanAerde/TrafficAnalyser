package be.kdg.processor.persistence;

import be.kdg.processor.models.options.Option;
import be.kdg.processor.models.options.OptionKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, String> {
}
