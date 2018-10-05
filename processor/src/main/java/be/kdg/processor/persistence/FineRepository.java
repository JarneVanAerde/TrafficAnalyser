package be.kdg.processor.persistence;

import be.kdg.processor.models.fines.Fine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface FineRepository extends JpaRepository<Fine, Integer> {
}
