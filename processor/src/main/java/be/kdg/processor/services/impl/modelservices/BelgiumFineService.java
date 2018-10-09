package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.fines.EmissionFine;
import be.kdg.processor.models.fines.FineType;
import be.kdg.processor.models.fines.SpeedFine;
import be.kdg.processor.persistence.FineRepository;
import be.kdg.processor.services.api.FineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service to create fines.
 * The fines are bound to laws of Belgium.
 */
@Service
public class BelgiumFineService implements FineService {
    private final FineRepository fineRepo;

    @Autowired
    public BelgiumFineService(FineRepository finrRepo) {
        this.fineRepo = finrRepo;
    }

    /**
     * Creates an emission fine and saves that to the database.
     *
     * @return a newly inserted emission fine.
     */
    @Override
    public EmissionFine createEmissionFine(double amount, int ownerEuroNorm, int legalEuroNorm, CameraMessage emmisionMessage) {
        EmissionFine emissionFine = new EmissionFine(FineType.EMISSiON_FINE, amount, ownerEuroNorm, legalEuroNorm, emmisionMessage);
        return fineRepo.save(emissionFine);
    }

    /**
     * Creates a speed fine and saves that to the database
     *
     * @return a newly inserted speed fine.
     */
    @Override
    public SpeedFine createSpeedFine(double amount, double carSpeed, double legalSpeed, CameraMessage enterCamera, CameraMessage exitCamera) {
        SpeedFine speedFine = new SpeedFine(FineType.SPEED_FINE, amount, carSpeed, legalSpeed, enterCamera, exitCamera);
        return fineRepo.save(speedFine);
    }
}
