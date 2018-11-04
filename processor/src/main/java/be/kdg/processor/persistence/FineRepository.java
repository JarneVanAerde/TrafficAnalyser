package be.kdg.processor.persistence;

import be.kdg.processor.models.fines.Fine;
import be.kdg.processor.models.fines.FineType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Integer> {
    List<Fine> getFinesByFineType(FineType fineType);
}
