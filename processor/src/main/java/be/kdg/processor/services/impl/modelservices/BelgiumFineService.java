package be.kdg.processor.services.impl.modelservices;

import be.kdg.processor.models.cameras.CameraMessage;
import be.kdg.processor.models.fines.EmissionFine;
import be.kdg.processor.models.fines.Fine;
import be.kdg.processor.models.fines.FineType;
import be.kdg.processor.models.fines.SpeedFine;
import be.kdg.processor.models.options.OptionKey;
import be.kdg.processor.models.vehicles.Vehicle;
import be.kdg.processor.persistence.FineRepository;
import be.kdg.processor.services.api.FineService;
import be.kdg.processor.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    private final CameraMessageService cameraMessageService;
    private final OptionService optionService;

    @Autowired
    public BelgiumFineService(FineRepository finrRepo, VehicleService vehicleService, CameraMessageService cameraMessageService, OptionService optionService) {
        this.fineRepo = finrRepo;
        this.vehicleService = vehicleService;
        this.cameraMessageService = cameraMessageService;
        this.optionService = optionService;
    }

    /**
     * Creates an emission fine and saves that to the database.
     * After the fine is created it is linked to a vehicle
     */
    @Override
    public void createEmissionFine(int ownerEuroNorm, int legalEuroNorm, CameraMessage emmisionMessage, String plateId) throws ServiceException {
        double amount = caculateFine(FineType.EMISSiON_FINE, legalEuroNorm, ownerEuroNorm);
        emmisionMessage = cameraMessageService.saveMessage(emmisionMessage);
        EmissionFine emissionFine = new EmissionFine(FineType.EMISSiON_FINE, amount, ownerEuroNorm, legalEuroNorm, emmisionMessage);
        saveFine(emissionFine);

        Vehicle vehicle = vehicleService.getVehicle(plateId);
        vehicle.addFine(emissionFine);
        vehicleService.saveVehicle(vehicle);
    }

    /**
     * Creates a speed fine and saves that to the database
     */
    @Override
    public void createSpeedFine(double carSpeed, double legalSpeed, CameraMessage enterCamera, CameraMessage exitCamera, String plateId) throws ServiceException {
        double amount = caculateFine(FineType.SPEED_FINE, legalSpeed, carSpeed);
        enterCamera = cameraMessageService.saveMessage(enterCamera);
        exitCamera = cameraMessageService.saveMessage(exitCamera);
        SpeedFine speedFine = new SpeedFine(FineType.SPEED_FINE, amount, carSpeed, legalSpeed, enterCamera, exitCamera);
        saveFine(speedFine);

        Vehicle vehicle = vehicleService.getVehicle(plateId);
        vehicle.addFine(speedFine);
        vehicleService.saveVehicle(vehicle);
    }

    private double caculateFine(FineType fineType, double legal, double actual) throws ServiceException {
        switch (fineType) {
            case EMISSiON_FINE:
                return (legal - actual) * optionService.getOptionValue(OptionKey.EMISSION_FAC);
            case SPEED_FINE:
                return (actual - legal) * optionService.getOptionValue(OptionKey.SPEED_FAC);
            default:
                return 200.0;
        }
    }

    public Fine saveFine(Fine fine) {
        return fineRepo.save(fine);
    }

    /**
     * This method prevents that a vehicle gets more than one
     * emission fine a day.
     *
     * @param plateId used to retrieve the vehicle.
     * @return true if their is already an emission fine created for today, and false if the opposite is true
     */
    @Override
    public boolean checkIfAlreadyHasEmissionfine(String plateId) throws ServiceException {
        Vehicle vehicle = vehicleService.getVehicle(plateId);
        String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        Optional<Fine> optionalFine = vehicle.getFines().stream()
                .filter(f -> f.getFineType() == FineType.EMISSiON_FINE)
                .filter(f -> f.getCreationDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                        .equalsIgnoreCase(today))
                .findAny();

        return optionalFine.isPresent();
    }

    @Override
    public List<Fine> getFines() {
        return fineRepo.findAll();
    }

    @Override
    public Fine getFine(int id) throws ServiceException {
        return fineRepo.findById(id)
                .orElseThrow(() -> new ServiceException(getClass().getSimpleName() + ": fine with cameraId " + id + " was not found in the database"));
    }

    @Override
    public Fine approveFine(int id) throws ServiceException {
        Fine fineToUpdate = getFine(id);
        fineToUpdate.setApproved(true);
        return saveFine(fineToUpdate);
    }

    public Fine changeAmount(int id, double amount, String motivation) throws ServiceException {
        Fine fineToUpdate = getFine(id);
        fineToUpdate.setAmount(amount);
        fineToUpdate.setChangeAmountMotivation(motivation);
        return saveFine(fineToUpdate);
    }

    @Override
    public Fine deleteFine(int id) throws ServiceException {
        Fine fineToRemove = getFine(id);
        fineRepo.delete(fineToRemove);
        return fineToRemove;
    }
}
