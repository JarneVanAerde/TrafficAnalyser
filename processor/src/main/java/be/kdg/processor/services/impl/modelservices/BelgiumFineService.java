package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.fines.EmissionFine;
import be.kdg.processor.models.fines.Fine;
import be.kdg.processor.models.fines.FineType;
import be.kdg.processor.models.fines.SpeedFine;
import be.kdg.processor.models.vehicles.Vehicle;
import be.kdg.processor.persistence.FineRepository;
import be.kdg.processor.services.api.FineService;
import be.kdg.processor.services.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Service to create fines.
 * The fines are bound to laws of Belgium.
 */
@Service
@Transactional
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
    public EmissionFine createEmissionFine(double amount, int ownerEuroNorm, int legalEuroNorm, CameraMessage emmisionMessage, String plateId) throws PersistenceException {
        EmissionFine emissionFine = new EmissionFine(FineType.EMISSiON_FINE, amount, ownerEuroNorm, legalEuroNorm, emmisionMessage);
        fineRepo.save(emissionFine);

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
    public SpeedFine createSpeedFine(double amount, double carSpeed, double legalSpeed, CameraMessage enterCamera, CameraMessage exitCamera, String plateId) throws PersistenceException {
        SpeedFine speedFine = new SpeedFine(FineType.SPEED_FINE, amount, carSpeed, legalSpeed, enterCamera, exitCamera);
        fineRepo.saveAndFlush(speedFine);

        Vehicle vehicle = vehicleService.getVehicle(plateId);
        vehicle.addFine(speedFine);
        vehicleService.saveVehicle(vehicle);
        return speedFine;
    }

    /**
     * This method prevents that a vehicle gets more than one
     * emission fine a day.
     *
     * @param plateId used to retrieve the vehicle.
     * @return true if their is already an emission fine created for today, and false if the opposite is true
     */
    @Override
    public boolean checkIfAlreadyHasEmissionfine(String plateId) throws PersistenceException {
        Vehicle vehicle = vehicleService.getVehicle(plateId);
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Optional<Fine> optionalFine = vehicle.getFines().stream()
                .filter(f -> f instanceof EmissionFine)
                .filter(f -> ((EmissionFine) f).getEmmisionMessage().getTimestamp()
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equalsIgnoreCase(today))
                .findAny();

        return optionalFine.isPresent();
    }
}
