package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.fines.EmissionFine;
import be.kdg.processor.models.fines.FineType;
import be.kdg.processor.models.fines.SpeedFine;
import be.kdg.processor.models.vehicles.Vehicle;
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
    private final VehicleService vehicleService;

    @Autowired
    public BelgiumFineService(FineRepository finrRepo, VehicleService vehicleService) {
        this.fineRepo = finrRepo;
        this.vehicleService = vehicleService;
    }

    /**
     * Creates an emission fine and saves that to the database.
     * After the fine is created it is linked to a vehicle.
     *
     * @return a newly inserted emission fine.
     */
    @Override
    public EmissionFine createEmissionFine(double amount, int ownerEuroNorm, int legalEuroNorm, CameraMessage emmisionMessage, String plateId) {
        EmissionFine emissionFine = new EmissionFine(FineType.EMISSiON_FINE, amount, ownerEuroNorm, legalEuroNorm, emmisionMessage);
        fineRepo.saveAndFlush(emissionFine);

        Vehicle vehicle = vehicleService.getVehicle(plateId);
        vehicle.addFine(emissionFine);
        vehicleService.saveVehicle(vehicle);
        return emissionFine;
    }

    /**
     * Creates a speed fine and saves that to the database
     *
     * @return a newly inserted speed fine.
     */
    @Override
    public SpeedFine createSpeedFine(double amount, double carSpeed, double legalSpeed, CameraMessage enterCamera, CameraMessage exitCamera) {
        SpeedFine speedFine = new SpeedFine(FineType.SPEED_FINE, amount, carSpeed, legalSpeed, enterCamera, exitCamera);
        return fineRepo.saveAndFlush(speedFine);
    }
}
